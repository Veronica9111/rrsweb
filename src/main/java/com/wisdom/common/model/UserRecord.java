package com.wisdom.common.model;

public class UserRecord implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer inspect;
    private Integer recognize;
    private String company;
    private String mail;
    private Integer active;


 
 

 
    public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Integer getInspect(){
    	return inspect;
    }
    
    public void setInspect(Integer inspect){
    	this.inspect = inspect;
    }
    
    public Integer getRecognize(){
    	return recognize;
    }
  
    public void setRecognize(Integer recognize){
    	this.recognize = recognize;
    }
 
    public String getCompany(){
    	return company;
    }
    
    public void setCompany(String company){
    	this.company = company;
    }
    
    public String getMail(){
    	return mail;
    }
    
    public void setMail(String mail){
    	this.mail = mail;
    }
}
