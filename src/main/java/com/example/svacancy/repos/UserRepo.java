package com.example.svacancy.repos;

import com.example.svacancy.Model.User;
import com.example.svacancy.Model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String userName);
    User findByEmail(String email);
    User findByActivationCode(String code);

//    @Query("SELECT * FROM Vacancy WHERE numberOfResponded IS NOT NULL AND id = :ids")
//    Page<User> findByVacancy(Pageable pageable, @Param("ids") Long ids);
}
