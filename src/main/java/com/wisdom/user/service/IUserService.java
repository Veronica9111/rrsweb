package com.wisdom.user.service;

public interface IUserService {

	public Boolean checkUserValidate(String id, String password);
	
	public Boolean addUser(String name, String company);
	
	
}
