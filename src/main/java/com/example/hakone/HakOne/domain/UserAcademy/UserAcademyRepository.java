package com.example.hakone.HakOne.domain.UserAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAcademyRepository extends JpaRepository<UserAcademy, Long> {
    Optional<UserAcademy> findById(Long id);
    Optional<UserAcademy> findByMember_IdAndAcademy_Id(Long userId, Long academyId);

    List<UserAcademy> findAllByMember_Id(Long userId);

    @Query("SELECT ua.academy.id FROM UserAcademy ua WHERE ua.member.id = :userId")
    List<Long> findAcademyIdsByUserId(@Param("userId") Long userId);
}