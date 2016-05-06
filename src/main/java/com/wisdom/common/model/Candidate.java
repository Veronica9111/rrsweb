package com.wisdom.common.model;

public class Candidate implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String value;
    private String type;
    private Integer confidence;
    private Integer invoiceId;

 
    public Candidate() {
        super();
    }
    
    public Candidate(Integer id, String value, String type, Integer confidence, Integer invoiceId){
    	this.id = id;
    	this.value = value;
    	this.type = type;
    	this.confidence = confidence;
    	this.invoiceId = invoiceId;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
    
    
 
}
