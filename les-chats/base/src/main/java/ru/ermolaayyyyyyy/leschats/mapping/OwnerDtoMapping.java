package ru.ermolaayyyyyyy.leschats.mapping;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import ru.ermolaayyyyyyy.leschats.entities.Owner;
import ru.ermolaayyyyyyy.leschats.dto.CatDto;
import ru.ermolaayyyyyyy.leschats.dto.OwnerDto;
import ru.ermolaayyyyyyy.leschats.dto.SecureOwnerDto;

@UtilityClass
@ExtensionMethod(CatDtoMapping.class)
public class OwnerDtoMapping {
    public static OwnerDto asDto(Owner owner){
        return new OwnerDto(owner.getName(), owner.getBirthDate(), owner.getCats().stream().map(CatDtoMapping::asDto).toList(), owner.getId());
    }

    public static SecureOwnerDto asSecureDto(OwnerDto owner){
        return new SecureOwnerDto(owner.name(), owner.birthDate(), owner.cats().stream().map(CatDto::id).toList(), owner.id());
    }
}
