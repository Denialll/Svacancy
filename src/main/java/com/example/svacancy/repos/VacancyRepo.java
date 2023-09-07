package com.example.svacancy.repos;

import com.example.svacancy.Model.Company;
import com.example.svacancy.Model.Vacancy;
import com.example.svacancy.Model.User;
import com.example.svacancy.Model.dto.VacancyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepo extends CrudRepository<Vacancy, Long> {
    @Query("select new com.example.svacancy.Model.dto.VacancyDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Vacancy m left join m.likes ml " +
            "group by m")
    Page<VacancyDto> findAll(Pageable pageable, @Param("user") User user);



    @Query("select new com.example.svacancy.Model.dto.VacancyDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Vacancy m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m")
    Page<VacancyDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user);

    @Query("select new com.example.svacancy.Model.dto.VacancyDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Vacancy m left join m.likes ml " +
            "where m.author = :author " +
            "group by m")
    Page<VacancyDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);

    @Query("select new com.example.svacancy.Model.dto.VacancyDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Vacancy m left join m.likes ml " +
            "where m.company = :company " +
            "group by m")
    Page<VacancyDto> findByCompany(Pageable pageable, @Param("company") Company company, @Param("user") User user);

    Vacancy findById(String id);
}

