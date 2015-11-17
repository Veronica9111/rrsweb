package com.wisdom.role.service;

import java.util.List;

public interface IRoleService {

	Boolean addRole(String roleName, List<String>permissionNames);
}
