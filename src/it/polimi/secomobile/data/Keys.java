package it.polimi.secomobile.data;

public enum Keys {
	

	SUBJECT_ID("subjectID"),
	MART_ID("MartId"),
	ACCESS_PATTERN_ID("accessPatternId"),
	INTERFACE_ID("InterfaceId"),
	CONNECTION_ID("connectionId"),
	
	SELECTED_MART_NAME("selectedMartName"),
	SELECTED_AP_NAME("selectedAP"),
	SELECTED_INTERFACE_NAME("selectedInterface"),
	SELECTED_TUPLE("selectedTuple"),
	
	QUERY_INFO("queryInfo"),
	ATTRIBUTE_LIST("attributeList"),
	ATTRIBUTE_NAME("attribute"),
	ATTRIBUTE_ID("attributeId"),
	ATTRIBUTE_VALUE("value"),
	ATTRIBUTE_DATATYPE("datatype"), 
	
	IS_CONNECTION("isConnection"), 
	;
	private String text;
	
	Keys(String text) {
      this.text = text;
    }
	
	@Override
	public String toString() {
	  return this.text;
	}
	
	public static Keys fromString(String text) {
	  if (text != null) {
	    for (Keys b : Keys.values()) {
	      if (text.equalsIgnoreCase(b.text)) 
	        return b;
	    }
	  }
	  throw new IllegalArgumentException(
			  "No constant with text " + text + " found");
	}
}

