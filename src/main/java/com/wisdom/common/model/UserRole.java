package com.wisdom.common.model;

import java.io.Serializable;
import java.util.List;

public class UserRole implements Serializable {
    
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
  // private String mail;
  // private String company;
   //private Boolean active;
   private List<Role> roles;
   



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

   public List<Role> getRole() {
	   return roles;
   }
   
   public void setRoles(List<Role> roles){
	   this.roles = roles;
   }
 /*
   public String getMail(){
	   return mail;
   }
   
   public void setMail(String mail){
	   this.mail = mail;
   }

   public String getCompany(){
	   return company;
   }
   
   public void setCompany(){
	   this.company = company;
   }
   
   public Boolean getActive(){
	   return active;
   }
   
   public void setActive(){
	   this.active = active;
   }
   */

}