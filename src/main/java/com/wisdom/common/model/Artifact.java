package com.wisdom.common.model;

import java.sql.Timestamp;

public class Artifact implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer invoice_id;
    private String supplier_name;
    private String type;
    private Double tax;
    private Double amount;
    private Integer number;
    private Timestamp created_time;
    private Integer is_fa;

    public Artifact() {
        super();
    }
    
    public Artifact(Integer id, Integer invoice_id, String supplier_name, String type, Double tax, Double amount, Integer number, Timestamp created_time, Integer is_fa){
    	this.id = id;
    	this.invoice_id = invoice_id;
    	this.supplier_name = supplier_name;
    	this.type = type;
    	this.tax = tax;
    	this.amount = amount;
    	this.number = number;
    	this.created_time = created_time;
    	this.is_fa = is_fa;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
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

	public Timestamp getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}

	public Integer getIs_fa() {
		return is_fa;
	}

	public void setIs_fa(Integer is_fa) {
		this.is_fa = is_fa;
	}

	
    
    
 
}
