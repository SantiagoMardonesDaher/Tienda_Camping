package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Role;
import com.uade.tpo.demo.entity.dto.RoleRequest;
import com.uade.tpo.demo.exceptions.RoleDuplicateException;
import com.uade.tpo.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<Role>> getRoles(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(roleService.getRoles(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(roleService.getRoles(PageRequest.of(page, size)));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        Optional<Role> result = roleService.getRoleById(roleId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody RoleRequest roleRequest) throws RoleDuplicateException {
        Role result = roleService.createRole(roleRequest.getDescription());
        return ResponseEntity.created(URI.create("/roles/" + result.getId())).body(result);
    }
}
