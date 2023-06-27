package ru.ermolaayyyyyyy.leschats.dto;

import java.time.LocalDate;
import java.util.List;

public record SecureOwnerDto(String name, LocalDate birthDate, List<Integer> cats, int id) implements IOwnerDto {
}
