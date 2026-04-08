var App = (function () {

    var mappings = [{ oldColumn: "", newColumn: "" }];

    var mappingRows = document.getElementById("mappingRows");
    var inputQuery = document.getElementById("inputQuery");
    var outputQuery = document.getElementById("outputQuery");
    var errorMsg = document.getElementById("errorMsg");
    var totalBadge = document.getElementById("totalBadge");
    var changeLog = document.getElementById("changeLog");

    function renderMappings() {
        var html = "";
        var i;

        for (i = 0; i < mappings.length; i++) {
            html += '<div class="mapping-row">';
            html += '<input type="text" placeholder="기존 컬럼명" value="' + escapeHtml(mappings[i].oldColumn) + '" ';
            html += 'oninput="App.updateMapping(' + i + ', \'oldColumn\', this.value)" />';
            html += '<input type="text" placeholder="새 컬럼명" value="' + escapeHtml(mappings[i].newColumn) + '" ';
            html += 'oninput="App.updateMapping(' + i + ', \'newColumn\', this.value)" />';
            html += '<button type="button" onclick="App.removeMapping(' + i + ')">삭제</button>';
            html += '</div>';
        }

        mappingRows.innerHTML = html;
    }

    function escapeHtml(value) {
        if (!value) {
            return "";
        }

        return value
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;");
    }

    function updateMapping(index, field, value) {
        mappings[index][field] = value;
    }

    function addMapping() {
        mappings.push({ oldColumn: "", newColumn: "" });
        renderMappings();
    }

    function removeMapping(index) {
        mappings.splice(index, 1);

        if (mappings.length === 0) {
            mappings.push({ oldColumn: "", newColumn: "" });
        }

        renderMappings();
    }

    function loadSample() {
        mappings = [
            { oldColumn: "CUST_NO", newColumn: "CUSTOMER_ID" },
            { oldColumn: "ACCT_NUM", newColumn: "ACCOUNT_NUMBER" },
            { oldColumn: "TB_BANCA_CONT", newColumn: "TB_BANCASSURANCE_CONTRACT" }
        ];

        inputQuery.value =
            "SELECT CUST_NO, ACCT_NUM\n" +
            "  FROM TB_BANCA_CONT\n" +
            " WHERE CUST_NO = :custNo";

        outputQuery.value = "";
        errorMsg.innerHTML = "";
        totalBadge.innerHTML = "";
        changeLog.innerHTML = "";

        renderMappings();
    }

    function clearAll() {
        mappings = [{ oldColumn: "", newColumn: "" }];
        inputQuery.value = "";
        outputQuery.value = "";
        errorMsg.innerHTML = "";
        totalBadge.innerHTML = "";
        changeLog.innerHTML = "";
        renderMappings();
    }

    function convert() {
        var query = inputQuery.value;
        var validMappings = [];
        var i;
        var xhr;
        var payload;

        errorMsg.innerHTML = "";
        totalBadge.innerHTML = "";
        changeLog.innerHTML = "";

        if (!query || query.trim() === "") {
            errorMsg.innerHTML = "쿼리를 입력해주세요.";
            return;
        }

        for (i = 0; i < mappings.length; i++) {
            if (mappings[i].oldColumn && mappings[i].newColumn) {
                validMappings.push(mappings[i]);
            }
        }

        if (validMappings.length === 0) {
            errorMsg.innerHTML = "컬럼 매핑 정보를 입력해주세요.";
            return;
        }

        payload = {
            query: query,
            mappings: validMappings
        };

        xhr = new XMLHttpRequest();
        xhr.open("POST", "api/convert", true);
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");

        xhr.onreadystatechange = function () {
            var response;
            var logHtml;
            var j;

            if (xhr.readyState !== 4) {
                return;
            }

            try {
                response = JSON.parse(xhr.responseText);
            } catch (e) {
                errorMsg.innerHTML = "서버 응답을 처리할 수 없습니다.";
                return;
            }

            if (xhr.status >= 200 && xhr.status < 300 && response.success) {
                outputQuery.value = response.convertedQuery || "";
                totalBadge.innerHTML = "총 치환 수: " + response.totalReplaced;

                logHtml = "";
                if (response.changeLogs && response.changeLogs.length > 0) {
                    for (j = 0; j < response.changeLogs.length; j++) {
                        logHtml += "<div>";
                        logHtml += response.changeLogs[j].oldColumn + " → "
                                + response.changeLogs[j].newColumn + " ("
                                + response.changeLogs[j].count + "회)";
                        logHtml += "</div>";
                    }
                }

                changeLog.innerHTML = logHtml;
            } else {
                errorMsg.innerHTML = response.errorMessage || "변환 중 오류가 발생했습니다.";
            }
        };

        xhr.onerror = function () {
            errorMsg.innerHTML = "서버 호출에 실패했습니다.";
        };

        xhr.send(JSON.stringify(payload));
    }

    renderMappings();

    return {
        updateMapping: updateMapping,
        addMapping: addMapping,
        removeMapping: removeMapping,
        loadSample: loadSample,
        clearAll: clearAll,
        convert: convert
    };
})();
