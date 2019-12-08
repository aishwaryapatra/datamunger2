package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {
	private String queryString;
	private String file;
	private String baseQuery;
	private List<String> fields;
	private String QUERY_TYPE;
	private List<Restriction> restriction;
	private List<String> logicalOperators;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> orderByFields;
	private List<String> groupByFields;
	
	public void setQueryString( String queryString) {
		this.queryString=queryString;
	}
	public String getQueryString() {
		return queryString;
	}
	
	public String getFileName() {

		return this.file;
	}
	public void setFileName(String file) {
		this.file=file;
	}

	public String getBaseQuery() {

		return this.baseQuery;
	}
	public void setBaseQuery(String baseQuery) {
		this.baseQuery=baseQuery;
	}

	public List<Restriction> getRestrictions() {
		return this.restriction;
	}
	public void setRestrictions(List<Restriction> restriction) {
		this.restriction=restriction;
	}

	public List<String> getLogicalOperators() {
		return this.logicalOperators;
	}
	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators=logicalOperators;
	}
	

	public List<String> getFields() {
		return this.fields;
	}
	public void setFields(List<String> fields) {
		this.fields=fields;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		return this.aggregateFunctions;
	}
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions=aggregateFunctions;
	}

	public List<String> getGroupByFields() {
     
     return this.groupByFields;
		
	}
	
	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}
	public List<String> getOrderByFields() {
	     return this.orderByFields;
	}
	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}
}