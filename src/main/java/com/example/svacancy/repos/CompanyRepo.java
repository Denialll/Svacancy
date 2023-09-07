package com.example.svacancy.repos;

import com.example.svacancy.Model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepo extends CrudRepository<Company, Long> {
    Optional<Company> findById(Long id);
    @Query("SELECT c FROM Company c WHERE c.active = false")
    List<Company> findAll();



}
