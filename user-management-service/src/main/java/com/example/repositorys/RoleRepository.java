package com.example.repositorys;

import com.example.models.roles.Role;
import com.example.models.roles.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(RoleName name);
}
