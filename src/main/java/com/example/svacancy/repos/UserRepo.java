package com.example.svacancy.repos;

import com.example.svacancy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String userName);
    User findByEmail(String email);
    User findByActivationCode(String code);

}
