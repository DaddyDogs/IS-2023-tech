package ru.ermolaayyyyyyy.leschats.mapping;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import ru.ermolaayyyyyyy.leschats.entities.User;
import ru.ermolaayyyyyyy.leschats.dto.UserDto;

import java.time.LocalDate;

@UtilityClass
@ExtensionMethod(UserDtoMapping.class)
public class UserDtoMapping {
    public static UserDto asDto(User user, String name, LocalDate birthdate){
        return new UserDto(user.getId(), user.getOwner().getId(), name, birthdate);
    }
}
