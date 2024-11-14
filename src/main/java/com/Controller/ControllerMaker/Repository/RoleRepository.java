package com.Controller.ControllerMaker.Repository;

import com.Controller.ControllerMaker.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
