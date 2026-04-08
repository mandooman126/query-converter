<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쿼리 변환기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <h1>쿼리 변환기</h1>

    <div>
        <h3>원본 쿼리</h3>
        <textarea id="inputQuery" rows="10" cols="80" placeholder="SQL을 입력하세요"></textarea>
    </div>

    <br>

    <div>
        <h3>컬럼 매핑</h3>
        <div id="mappingRows"></div>
        <button type="button" onclick="App.addMapping()">매핑 추가</button>
        <button type="button" onclick="App.loadSample()">샘플 불러오기</button>
        <button type="button" onclick="App.clearAll()">초기화</button>
    </div>

    <br>

    <div>
        <button type="button" onclick="App.convert()">변환 실행</button>
        <div id="errorMsg" style="color:red; margin-top:10px;"></div>
    </div>

    <br>

    <div>
        <h3>변환 결과</h3>
        <textarea id="outputQuery" rows="10" cols="80" readonly></textarea>
        <div id="totalBadge" style="margin-top:10px;"></div>
        <div id="changeLog" style="margin-top:10px;"></div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
