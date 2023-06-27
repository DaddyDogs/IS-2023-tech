package ru.ermolaayyyyyyy.leschats.dto;

import java.time.LocalDate;

public record UserDto(int id, int ownerId, String name, LocalDate birthdate) { }
