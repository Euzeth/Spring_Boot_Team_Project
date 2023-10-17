package com.example.demo.Domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Member {
    @Id
    @Column(length=255)
    private String username;
    private String password;
    private String role;
    private String phone;
    private String zipcode;
    private String addr1;
    private String addr2;
    private String name;
    private String email;

    // OAuth2 Added
    private String provider;
    private String providerId;
}
