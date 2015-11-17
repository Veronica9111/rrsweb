package com.wisdom.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.PermissionMapper;
import com.wisdom.common.mapper.UserMapper;
import com.wisdom.common.model.Permission;
import com.wisdom.permission.service.IPermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService{

	  @Autowired
	  private	PermissionMapper permissionMapper;


  
	private static final Logger logger = LoggerFactory
			.getLogger(PermissionServiceImpl.class);
	
	public void setPermissionMapper(PermissionMapper permissionMapper) {
		    this.permissionMapper = permissionMapper;
	}
	
	@Override
	public boolean addPermission(Permission permission) {
		boolean boo=false;
		boo=permissionMapper.addPermission(permission.getName(),permission.getInvokeName(),permission.getId());
		return boo;
	}

	@Override
	public boolean deletePermission(Permission permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, String>> getAllPermission() {
		List<Permission>pList=permissionMapper.getAllPermission();
		List<Map<String, String>> gapList = new ArrayList<>();
		for(Permission per:pList){
			Map<String, String> newMap = new HashMap<>();
			newMap.put(per.getName(), per.getName().toString());
			newMap.put(per.getInvokeName(), per.getInvokeName().toString());
			newMap.put(Integer.toString(per.getId()), per.getId().toString());
			gapList.add(newMap);
		}
		return gapList;
	}

	@Override
	public List<Map<String, String>> getPermissionByName(String name) {
		List<Permission>pList=permissionMapper.getPermissionByName(name);;
		List<Map<String, String>> gapList = new ArrayList<>();
		for(Permission per:pList){
			Map<String, String> newMap = new HashMap<>();
			newMap.put(per.getName(), per.getName().toString());
			newMap.put(per.getInvokeName(), per.getInvokeName().toString());
			newMap.put(Integer.toString(per.getId()), per.getId().toString());
			gapList.add(newMap);
		}
		return gapList;
	}

}
