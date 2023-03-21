package com.example.hakone.HakOne.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByMember_IdAndAcademy_Id(Long userId, Long academyId);
}
