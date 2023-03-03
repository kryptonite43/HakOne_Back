package com.example.hakone.HakOne.domain.academy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Food {
    @Id
    @Column(name = "food_id")
    private String id;
    private String name;

    public Food(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
