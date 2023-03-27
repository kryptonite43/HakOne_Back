package com.example.hakone.HakOne.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByMember_IdAndAcademy_Id(Long userId, Long academyId);
    @Query("SELECT r FROM Review r WHERE r.academy.id = :academyId")
    List<Review> findAllByAcademy_Id(@Param("academyId") Long academyId);

    @Query("SELECT r FROM Review r WHERE r.member.id = :userId")
    List<Review> findAllByMember_Id(@Param("userId") Long userId);

    Optional<Review> findByAcademy_IdAndId(Long academyId, Long reviewId);
}
