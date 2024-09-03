package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public List<Role> getAllRoles() {
        return Arrays.asList(Role.values());
    }

    @Override
    public Role getRoleByName(String name) {
        try {
            return Role.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null; // o lanzar una excepción personalizada
        }
    }
}
