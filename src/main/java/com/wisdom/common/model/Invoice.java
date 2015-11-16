package com.wisdom.common.model;

import java.sql.Timestamp;

public class Invoice {

	private static final long serialVersionUID = 1L;
    private String id;
    private Integer uid;
    private String name;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private Integer priority;
    private String path;
    private String company;
    private Integer exported; 
    private String document;
    private String status;

 
    public Invoice() {
        super();
    }
 
    public Invoice(String id, Timestamp createdTime, String path, String company, String document) {
        super();
        this.id = id;
        this.createdTime = createdTime;
        this.path = path;
        this.company = company;
        this.document = document;
    }
 

 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Integer getUID(){
    	return uid;
    }
    
    public void setUID(Integer uid){
    	this.uid = uid;
    }
    
    public Timestamp getCreatedTime(){
    	return createdTime;
    }
    
    public void setCreatedTime(Timestamp createdTime){
    	this.createdTime = createdTime;
    }
  
    public Timestamp getModifiedTime(){
    	return modifiedTime;
    }
    
    public void setModifiedTime(Timestamp modifiedTime){
    	this.modifiedTime = modifiedTime;
    }
 
    public Integer getPriority(){
    	return priority;
    }
    
    public void setPriority(Integer priority){
    	this.priority = priority;
    }
    
    public String getPath(){
    	return path;
    }
    
    public void setPath(String path){
    	this.path = path;
    }
    
    public String getCompany(){
    	return company;
    }
    
    public void setCompany(String company){
    	this.company = company;
    }
    
    public Integer getExported(){
    	return exported;
    }
    
    public void setExported(Integer exported){
    	this.exported = exported;
    }
    
    public String getDocument(){
    	return document;
    }
    
    public void setDocument(String document){
    	this.document = document;
    }
    
    public String getStatus(){
    	return status;
    }
    
    public void setStatus(String status){
    	this.status = status;
    }
    @Override
    public String toString() {
        return "Permission [id=" + id + ", name=" + name + "]";
    }
}
