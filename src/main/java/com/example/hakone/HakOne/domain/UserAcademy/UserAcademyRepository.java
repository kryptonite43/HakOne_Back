package com.example.hakone.HakOne.domain.UserAcademy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAcademyRepository extends JpaRepository<UserAcademy, Long> {
    Optional<UserAcademy> findById(Long id);
    Optional<UserAcademy> findByMember_IdAndAcademy_Id(Long userId, Long academyId);

    List<UserAcademy> findAllByMember_Id(Long userId);
}