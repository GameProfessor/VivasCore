package vn.vivas.core.dao;

public class QueryParameter {
	String condition = "AND";		// "AND" or "OR".  Case insensitive
	String propertyName;
	Object value;
	String operator;				// "=", ">=", "<=", "<", ">", "like"
	public QueryParameter(String condition, String propertyName,String operator,  Object value) {
		super();
		this.condition = condition;
		this.propertyName = propertyName;
		this.value = value;
		this.operator = operator;
	}
	public QueryParameter(String propertyName,String operator,  Object value) {
		super();
		this.propertyName = propertyName;
		this.value = value;
		this.operator = operator;
	}
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
