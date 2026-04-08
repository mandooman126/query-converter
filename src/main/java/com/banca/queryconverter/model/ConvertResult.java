package com.banca.queryconverter.model;

import java.util.ArrayList;
import java.util.List;

public class ConvertResult {

    private boolean success;
    private String convertedQuery;
    private List<ChangeLog> changeLogs = new ArrayList<ChangeLog>();
    private int totalReplaced;
    private String errorMessage;

    public ConvertResult() {
    	
    }

    public ConvertResult(String convertedQuery, List<ChangeLog> changeLogs, int totalReplaced) {
        this.success = true;
        this.convertedQuery = convertedQuery;
        this.changeLogs = changeLogs;
        this.totalReplaced = totalReplaced;
    }

    public ConvertResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getConvertedQuery() {
        return convertedQuery;
    }

    public List<ChangeLog> getChangeLogs() {
        return changeLogs;
    }

    public int getTotalReplaced() {
        return totalReplaced;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static class ChangeLog {
        private String oldColumn;
        private String newColumn;
        private int count;

        public ChangeLog() {
        	
        }

        public ChangeLog(String oldColumn, String newColumn, int count) {
            this.oldColumn = oldColumn;
            this.newColumn = newColumn;
            this.count = count;
        }

        public String getOldColumn() {
            return oldColumn;
        }

        public String getNewColumn() {
            return newColumn;
        }

        public int getCount() {
            return count;
        }
    }
}
