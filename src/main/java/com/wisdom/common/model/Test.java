package com.wisdom.common.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
 
public class Test implements Serializable {
     
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;


 
    public Test() {
        super();
    }
 
    public Test(Integer id, String name) {
        super();
        this.name = name;

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
 
  
 
    @Override
    public String toString() {
        return "Test [id=" + id + ", name=" + name + "]";
    }
}