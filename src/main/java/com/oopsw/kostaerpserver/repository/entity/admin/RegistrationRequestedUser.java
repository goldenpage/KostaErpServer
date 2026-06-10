package com.oopsw.kostaerpserver.repository.entity.admin;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegistrationRequestedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @Column(nullable = false, unique = true)
    private String bId;

    @Column(nullable = false)
    private String hasPw;

    private String name;
    private String phone;
    private String StoreType;
    private String storeCategory;
    private boolean marketingAgree;

    @Column(nullable = false)
    private String documentPath;

    private String ocrConfidence;
    private String reason;
    private String reviewDate;

    @Enumerated(EnumType.STRING)
    private String reviewStatus;

}
