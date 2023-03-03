package com.example.hakone.HakOne.domain.academy;

import com.example.hakone.HakOne.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, String> {

}
