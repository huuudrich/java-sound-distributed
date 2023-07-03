package com.example.repositorys;

import com.example.models.roles.Role;
import com.example.models.roles.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getByName(RoleName name);
}
