package com.example.hakone.HakOne.domain.academy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademyRepository extends JpaRepository<Academy, Long> {
    Optional<Academy> findByAcademyName(String academyName);
}