package ru.ermolaayyyyyyy.leschats.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ermolaayyyyyyy.leschats.dto.ExceptionMessage;
import ru.ermolaayyyyyyy.leschats.exceptions.InvalidAttributeException;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler()
    public String handleException(ExceptionMessage ex) {
        logger.info(ex.getMessage());
//        if (Objects.equals(ex.exceptionType, "InvalidAttributeException")){
//            logger.info("Exception was thrown");
//            throw InvalidAttributeException.friendAlreadyExist(12, 14);
//        }
        return ex.message;
    }
}
