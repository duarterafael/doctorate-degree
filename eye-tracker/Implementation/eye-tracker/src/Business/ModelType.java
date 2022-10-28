package Business;

public enum ModelType {
	
	WITH_GUIDELINES_AND_COLORFUL("CD-COR_With guidelines and colorful"),
	WITH_GUIDELINES_AND_BLACK_AND_WHITE("CD-PB_With guidelines and black and white"),
	NO_GUIDELINES_AND_COLORFUL("SD-COR_No guidelines and colorful"),
	NO_GUIDELINES_AND_BLACK_AND_WHITE("SD-PB_No guidelines and black and white");

    private String description;

    ModelType(String description) {
        this.description = description;
    }

    public String getdescription() {
        return description;
    }
    
    public static ModelType getByDescription(String description)
    {
    	for (ModelType modelType : ModelType.values()) { 
    		if(modelType.getdescription().equals(description))
    		{
    			return modelType;
    		}
    	}
    	return null;
    }
}
