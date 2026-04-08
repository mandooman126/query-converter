package com.banca.queryconverter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.banca.queryconverter.model.ColumnMapping;
import com.banca.queryconverter.model.ConvertResult;

@Service
public class QueryConverterService {

    public ConvertResult convert(String query, List<ColumnMapping> mappings) {
        if (query == null || query.trim().isEmpty()) {
            return new ConvertResult("쿼리를 입력해주세요.");
        }

        if (mappings == null || mappings.isEmpty()) {
            return new ConvertResult("컬럼 매핑 정보를 입력해주세요.");
        }

        String convertedQuery = query;
        List<ConvertResult.ChangeLog> changeLogs = new ArrayList<ConvertResult.ChangeLog>();
        int totalReplaced = 0;

        for (ColumnMapping mapping : mappings) {
            if (mapping == null || !mapping.isValid()) {
                continue;
            }

            String oldColumn = mapping.getOldColumn().trim();
            String newColumn = mapping.getNewColumn().trim();

            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(oldColumn) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(convertedQuery);

            StringBuffer buffer = new StringBuffer();
            int count = 0;

            while (matcher.find()) {
                matcher.appendReplacement(buffer, Matcher.quoteReplacement(newColumn));
                count++;
            }

            matcher.appendTail(buffer);

            if (count > 0) {
                convertedQuery = buffer.toString();
                changeLogs.add(new ConvertResult.ChangeLog(oldColumn, newColumn, count));
                totalReplaced += count;
            }
        }

        return new ConvertResult(convertedQuery, changeLogs, totalReplaced);
    }
}
