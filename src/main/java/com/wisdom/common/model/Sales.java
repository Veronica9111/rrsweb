package com.wisdom.common.model;

public class Sales implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String saller_account;
    private String user_name;
    private String user_company;
    private String user_phone;
    private String latest_comment;
    private String status;
    private String updated_time;
    private String accountant;


 
    public Sales() {
        super();
    }
 
    public Sales(String saller_account, String user_name, String user_company, String user_phone, String latest_comment, String status, String updated_time, String accountant) {
        super();
        this.saller_account = saller_account;
        this.user_name = user_name;
        this.user_company = user_company;
        this.user_phone = user_phone;
        this.latest_comment = latest_comment;
        this.status = status;
        this.updated_time = updated_time;
        this.accountant = accountant;

    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getSaller_account() {
		return saller_account;
	}

	public void setSaller_account(String saller_account) {
		this.saller_account = saller_account;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_company() {
		return user_company;
	}

	public void setUser_company(String user_company) {
		this.user_company = user_company;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getLatest_comment() {
		return latest_comment;
	}

	public void setLatest_comment(String latest_comment) {
		this.latest_comment = latest_comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getAccountant() {
		return accountant;
	}

	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}
 

 
   
}
