package ru.ermolaayyyyyyy.leschats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ExceptionMessage extends Throwable implements Serializable{
    public String message;
    public String exceptionType;
}
