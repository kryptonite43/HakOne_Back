package com.example.hakone.HakOne.domain.classroom;

import com.example.hakone.HakOne.domain.academy.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Classroom, Long> {

}


//    Optional<Academy> findByAcademyName(String academyName);