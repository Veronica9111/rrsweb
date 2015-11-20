package com.wisdom.common.model;

import java.io.Serializable;

public class User implements Serializable {
    
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String mail;
   private String password;
   private String company;
   private Integer active;
   



   public User() {
       super();
   }

   public User(Integer id, String name, String password, String company, Integer active) {
       super();
       this.name = name;
       this.password = password;
       this.company = company;
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

   public String getPassword() {
	   return password;
   }
   
   public void setPassword(String password){
	   this.password = password;
   }
 
   public String getMail(){
	   return mail;
   }
   
   public void setMail(String mail){
	   this.mail = mail;
   }

   public String getCompany(){
	   return company;
   }
   
   public void setCompany(String company){
	   this.company = company;
   }
   
   public Integer getActive(){
	   return active;
   }
   
   public void setActive(Integer active){
	   this.active = active;
   }
   
   @Override
   public String toString() {
       return "User [id=" + id + ", name=" + name + "]";
   }
}