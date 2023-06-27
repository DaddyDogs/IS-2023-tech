package ru.ermolaayyyyyyy.leschats.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ControllerOwnerDto(
                       @NotBlank(message = "Name cannot be blank")
                       String name,
                       @PastOrPresent(message = "Date must be in past or present")
                       LocalDate birthDate) {
}
