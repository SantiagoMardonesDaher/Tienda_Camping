package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleByName(String name);
}
