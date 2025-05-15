package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
