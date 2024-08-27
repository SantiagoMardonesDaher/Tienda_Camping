package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Role;
import com.uade.tpo.demo.exceptions.RoleDuplicateException;
import com.uade.tpo.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> getRoles(PageRequest pageRequest) {
        return roleRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public Role createRole(String description) throws RoleDuplicateException {
        List<Role> existingRoles = roleRepository.findByDescription(description);
        if (!existingRoles.isEmpty()) {
            throw new RoleDuplicateException("A role with the same description already exists.");
        }
        Role role = new Role();
        role.setDescription(description);
        return roleRepository.save(role);
    }
}
