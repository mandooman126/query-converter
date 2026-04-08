package com.banca.queryconverter.model;

public class ColumnMapping {

	private String oldColumn;
	private String newColumn;
	
	public ColumnMapping() {
		
	}
	
	public ColumnMapping(String oldColumn, String newColumn) {
		this.oldColumn = oldColumn;
		this.newColumn = newColumn;
	}
	
	public String getOldColumn() {
		return oldColumn;
	}
	
	public void setOldColumn(String oldColumn) {
		this.oldColumn = oldColumn;
	}
	
	public String getNewColumn() {
		return newColumn;
	}
	
	public void setNewColumn(String newColumn) {
		this.newColumn = newColumn;
	}
	
	public boolean isValid() {
        return oldColumn != null && !oldColumn.trim().isEmpty()
            && newColumn != null && !newColumn.trim().isEmpty();
	}
}
