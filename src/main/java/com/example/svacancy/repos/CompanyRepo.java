package com.example.svacancy.repos;

import com.example.svacancy.Model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepo extends CrudRepository<Company, Long> {
}
