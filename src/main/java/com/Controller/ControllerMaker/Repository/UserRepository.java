package com.Controller.ControllerMaker.Repository;

import com.Controller.ControllerMaker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT p from User p WHERE "+
            "LOWER(p.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.password) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.imageName) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<User> searchUsers(String keyword);
}
