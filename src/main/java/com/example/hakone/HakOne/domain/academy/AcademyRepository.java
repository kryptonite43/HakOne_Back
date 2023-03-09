package com.example.hakone.HakOne.domain.academy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AcademyRepository extends JpaRepository<Academy, Long> {
    Optional<Academy> findByAcademyName(String academyName);
    @Query("SELECT p FROM Academy p ORDER BY p.id ASC")
    List<Academy> findAllDesc();
}
