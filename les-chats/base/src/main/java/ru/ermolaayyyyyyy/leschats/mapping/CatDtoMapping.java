package ru.ermolaayyyyyyy.leschats.mapping;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import ru.ermolaayyyyyyy.leschats.entities.Cat;
import ru.ermolaayyyyyyy.leschats.dto.CatDto;

@UtilityClass
@ExtensionMethod(CatDtoMapping.class)
public class CatDtoMapping {
    public static CatDto asDto(Cat cat){
        return new CatDto(cat.getName(),
                cat.getBirthDate(),
                cat.getBreed(),
                cat.getColor().toString(),
                cat.getOwner().getId(),
                cat.getFriends().stream().map(Cat::getId).toList(),
                cat.getId());
    }
}