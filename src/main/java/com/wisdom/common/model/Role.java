package com.wisdom.common.model;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;


 
    public Role() {
        super();
    }
 
    public Role(Integer id, String name) {
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
        return "Role [id=" + id + ", name=" + name + "]";
    }
}
