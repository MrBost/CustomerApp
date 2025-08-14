package com.fbn.case_study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fbn.case_study.utils.AttributeEncryptor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_account_no", columnList = "account_number")})
@Getter
@Setter
public class Customer extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(length = 25)
    private String phone;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(nullable = false, unique = true, length = 20)
    private String address;

    @Column(name = "nin")
    @Convert(converter = AttributeEncryptor.class)
    private String nin;

    @Column(name = "bvn")
    @Convert(converter = AttributeEncryptor.class)
    private String bvn;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Classification classification;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(nullable = false) private Integer kycLevel;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;
//    @Embedded
//    private NextOfKin nextOfKinDetails;

    public enum CustomerType { INDIVIDUAL, CORPORATE }
    public enum Classification { STANDARD, PEP, HNI }
    public enum CustomerStatus { ACTIVE, INACTIVE }
    @Embeddable
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NextOfKin {
        @Column(name = "kin_full_name", nullable = false)
        private String kinFullName;

        @Column(name = "kin_email")
        private String kinEmailAddress;

        @Column(name = "kin_phone")
        private String kinPhoneNo;

        @Column(name = "kin_address")
        private String kinAddress;

        @Column(name = "relationship")
        private String relationship;
    }
}
