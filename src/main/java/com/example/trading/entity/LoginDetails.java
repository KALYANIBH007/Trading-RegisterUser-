package com.example.trading.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "login_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetails {

    @Id
    private String loginId;
    private String password;
    private String loggedInStatus;
    private int userId;
}
