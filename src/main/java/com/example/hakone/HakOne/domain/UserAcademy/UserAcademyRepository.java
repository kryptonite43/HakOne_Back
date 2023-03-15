package com.example.hakone.HakOne.domain.UserAcademy;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAcademyRepository extends JpaRepository<UserAcademy, Long> {
    Optional<UserAcademy> findById(Long id);
    Optional<UserAcademy> findByMember_IdAndAcademy_Id(Long userId, Long academyId);

}