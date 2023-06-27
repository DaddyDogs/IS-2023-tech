package ru.ermolaayyyyyyy.leschats.dto;

import java.time.LocalDate;

public record UpdatedOwnerInfo(String name, LocalDate birthdate, int id) {
}
