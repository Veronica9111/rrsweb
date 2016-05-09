package com.wisdom.common.model;

import java.sql.Timestamp;

public class Artifact implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer invoiceId;
    private String supplierName;
    private String type;
    private Double tax;
    private Double amount;
    private Integer number;
    private Timestamp createdTime;
    private Integer isFa;

    public Artifact() {
        super();
    }
    
    public Artifact(Integer id, Integer invoiceId, String supplierName, String type, Double tax, Double amount, Integer number, Timestamp createdTime, Integer isFa){
    	this.id = id;
    	this.invoiceId = invoiceId;
    	this.supplierName = supplierName;
    	this.type = type;
    	this.tax = tax;
    	this.amount = amount;
    	this.number = number;
    	this.createdTime = createdTime;
    	this.isFa = isFa;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getIsFa() {
		return isFa;
	}

	public void setIsFa(Integer isFa) {
		this.isFa = isFa;
	}

	
    
    
 
}
