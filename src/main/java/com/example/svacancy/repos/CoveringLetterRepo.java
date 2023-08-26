package com.example.svacancy.repos;

import com.example.svacancy.Model.CoveringLetter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CoveringLetterRepo extends CrudRepository<CoveringLetter, Long> {

    List<CoveringLetter> findAll();

}
