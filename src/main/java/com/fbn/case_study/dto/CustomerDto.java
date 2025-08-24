package com.fbn.case_study.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fbn.case_study.entity.Customer;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;

public class CustomerDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Request {
        @NotBlank(message = "First name may not be blank")
        @Size(max=80) private String firstName;

        @NotBlank(message = "Last name may not be blank")
        @Size(max=100)
        private String lastName;

        private String address;

        @Email(message = "Must be a valid email address")
        @NotBlank
        private String email;

        @Pattern(regexp="^\\+\\d{10,15}$", message = "Must be a valid phone number in the format +234....")
        private String phone;

        @Pattern(regexp="^[0-9]{11}$", message = "NIN must be 11 digits")
        private String nin;

        @Pattern(regexp="^[0-9]{11}$", message = "BVN must be 11 digits")
        private String bvn;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        @Past(message = "Date of birth must be in the past")
        @NotNull(message = "Date of birth is required")
        private LocalDate dob;

        @Pattern(regexp="^[0-9]{10}$", message="Account number must be 10 digits")
        @NotBlank private String accountNumber;

        @NotNull
        private Customer.CustomerType customerType;
        @NotNull private Customer.Classification classification;

        @NotNull @Min(1) @Max(3)
        private Integer kycLevel;

        private String profilePicture;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String accountNumber;
        private String address;
        private String nin;
        private String bvn;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDate dob;
        private Customer.CustomerType customerType;
        private Customer.Classification classification;
        private Integer kycLevel;
        private Customer.CustomerStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDate createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDate updatedAt;
    }
}
