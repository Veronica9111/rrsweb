package com.wisdom.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Record implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer uid;
    private String invoice_id;
    private String action;
    private Timestamp created_time;


 
    public Record() {
        super();
    }
 
    public Record(Integer uid, String invoice_id, String action, Timestamp created_time) {
        super();
        this.uid = uid;
        this.invoice_id = invoice_id;
        this.action = action;
        this.created_time = created_time;

    }

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
    
    
 
}
