package ru.ermolaayyyyyyy.leschats.dto;

import org.springframework.beans.PropertyValues;

import java.time.LocalDate;

public interface IOwnerDto {
    String name();

    LocalDate birthDate();
    int id();
}
