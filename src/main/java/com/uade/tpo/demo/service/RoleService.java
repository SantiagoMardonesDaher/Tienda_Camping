package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Role;
import com.uade.tpo.demo.exceptions.RoleDuplicateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface RoleService {
    Page<Role> getRoles(PageRequest pageRequest);

    Optional<Role> getRoleById(Long roleId);

    Role createRole(String description) throws RoleDuplicateException;
}
