package com.example.trading.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Username is mandatory")
    private String userName;
    @Email(message = "Email should be Valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Phone no should be 10 digits")
    private String phoneNo;
    @NotBlank(message = "Pan No is mandatory")
    private String panNumber;
    @NotBlank(message = "Document Name is mandatory")
    private String documentName;
    @NotBlank(message = "Document Number is mandatory")
    private String documentNumber;
}
