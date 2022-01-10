package com.example.membermanage.repository;

import com.example.membermanage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);
    User findById(int id);

}
