package com.stackroute.datamunger.query.parser;
import java.util.*;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		queryParameter.setFileName(fileName(queryString));
		queryParameter.setBaseQuery(baseQuery(queryString));
		queryParameter.setGroupByFields(groupByFields(queryString));
		queryParameter.setOrderByFields(orderByFields(queryString));
		queryParameter.setLogicalOperators(logicalOperators(queryString));
    	queryParameter.setAggregateFunctions(aggregateFunctions(queryString));
		queryParameter.setFields(extractField(queryString));
		queryParameter.setRestrictions(extractConditions(queryString));
		
		
			return queryParameter;
	}
	
	public String fileName(String queryString) {
		int index=0;
		String arr[]=queryString.split(" ");
		for(int i=0;i<arr.length;i++) {
			if(arr[i].equals("from"))
			index=i;
		}
		String name=arr[index+1];

		return name;
	}
	public String baseQuery(String queryString) {
		int pos1=queryString.indexOf("from") +5;
		int pos2 = queryString.indexOf(" ",pos1);
        if(pos2 == -1)
            pos2 = queryString.length();
            
        
        queryString = queryString.substring(0,pos2);

		return queryString;
	}
	public List<String> groupByFields(String queryString) {
		queryString=queryString.toLowerCase();
		if (!queryString.contains("group by")) {
			return null;
		}
		String [] arr = queryString.split("group by | order by")[1].trim().split(" ");
         List<String> list = new ArrayList<String>(arr.length);
         for (String s:arr) {
         list.add( s );
        } 
     return list;
	}
	public List<String> orderByFields(String queryString) {
		if (!queryString.contains("order by")) {
			return null;
		}
		String [] arr = queryString.split("order by")[1].trim().split(" ");
		List<String> list = new ArrayList<String>(arr.length);
        for (String s:arr) {
        list.add( s );
       } 
    return list;
        
	}
	public List<String> logicalOperators(String queryString) {
		int pos=queryString.indexOf("where");
		if(pos==-1) {
			return null;
		}
		String arr[]=queryString.split(" ");
		ArrayList<String> ar= new ArrayList<String>();
		for(int i=0;i<arr.length;i++) {
			if(arr[i].equals("and")|| arr[i].equals("or")) {
				ar.add(arr[i]);
			}
		}
		return ar;
	}
	public List<String> extractField(String queryString) {
		List<String> field = new ArrayList<String>();
		String file1 = queryString;
		int index1 = file1.indexOf("select ");
		int index2 = file1.indexOf(" from");
		String filename1 = file1.substring(index1 + 7, index2);
		String[] fields = filename1.split(",");
		for (int i = 0; i < fields.length; i++) {
			field.add(fields[i]);
		}
		return field;
	}
	
	public List<Restriction> extractConditions(String queryString) {
		List<Restriction> result = null;
		
		String[] query1;
		
		String temp;
		String[] query2;
		String[] query3 = null;
		if (queryString.contains("where")) {
			result = new ArrayList<Restriction>();
			query1 = queryString.trim().split("where ");
			if (query1[1].contains("group by")) {
				query2 = query1[1].trim().split("group by");
				temp = query2[0];
			} else if (query1[1].contains("order by")) {
				query2 = query1[1].trim().split("order by");
				temp = query2[0];
			} else {
				temp = query1[1];
			}
			query3 = temp.trim().split(" and | or ");
			
			String[] opr = null;
			if (query3 != null) {
				for (int i = 0; i < query3.length; i++) {
					if (query3[i].contains("=")) {
						opr = query3[i].trim().split("\\W+");
						result.add(new Restriction(opr[0], opr[1], "="));
					} else if (query3[i].contains(">")) {
						opr = query3[i].trim().split("\\W+");
						result.add(new Restriction(opr[0], opr[1], ">"));
					} else if (query3[i].contains("<")) {
						opr = query3[i].trim().split("\\W+");
						result.add(new Restriction(opr[0], opr[1], "<"));
					}

				}
			}
			
		}
		return result;

	}
	
	
    public List<AggregateFunction> aggregateFunctions(String queryString) {
    	
    			 List<AggregateFunction> agg = new ArrayList<AggregateFunction>();
    			 int index1 = queryString.toLowerCase().indexOf("select");
    			 int index2 = queryString.toLowerCase().indexOf(" from");
    			 String query = queryString.toLowerCase().substring(index1 + 7, index2);
    			String[] arr = null;
    			arr = query.split(",");
    			for (int i = 0; i < arr.length; i++) {
    				if (arr[i].startsWith("max(") && arr[i].endsWith(")")
    						|| arr[i].startsWith("min(") && arr[i].endsWith(")")
    						|| arr[i].startsWith("avg(") && arr[i].endsWith(")")
    						|| arr[i].startsWith("sum") && arr[i].endsWith(")")) {
    					agg.add(new AggregateFunction(arr[i].substring(4, arr[i].length() - 1),
    							arr[i].substring(0, 3)));
    					
    				} else if (arr[i].startsWith("count(") && arr[i].endsWith(")")) {
    					agg.add(new AggregateFunction(arr[i].substring(6, arr[i].length() - 1),
    							arr[i].substring(0, 5)));
    					
    				}

    			}
    			return agg;	}
	
	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */

}