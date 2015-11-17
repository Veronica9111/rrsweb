package com.wisdom.common.model;

public class Permission implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String invoke_name;


 
    public Permission() {
        super();
    }
 
    public Permission(Integer id, String name, String invoke_name) {
        super();
        this.name = name;
        this.invoke_name = invoke_name;

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
 
    public String getInvokeName(){
    	return invoke_name;
    }
    
    public void setInvokeName(String invoke_name){
    	this.invoke_name = invoke_name;
    }
  
 
    @Override
    public String toString() {
        return " id=" + id + ", name=" + name + "";
    }
}
