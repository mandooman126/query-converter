package com.banca.queryconverter.model;

import java.util.ArrayList;
import java.util.List;

public class ConvertRequest {
	
	private String query;	// "SELECT CUST_NO FROM TB_BANCA_CONT"
	private List<ColumnMapping> mappings = new ArrayList<ColumnMapping>();	//mappings": [{ "oldColumn": "CUST_NO", "newColumn": "CUSTOMER_ID" }]
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public List<ColumnMapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<ColumnMapping> mappings) {
		this.mappings = mappings;
	}
}
