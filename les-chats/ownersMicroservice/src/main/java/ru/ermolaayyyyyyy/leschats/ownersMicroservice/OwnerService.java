package ru.ermolaayyyyyyy.leschats.ownersMicroservice;

import ru.ermolaayyyyyyy.leschats.dto.OwnerDto;
import ru.ermolaayyyyyyy.leschats.dto.OwnerInfo;
import ru.ermolaayyyyyyy.leschats.dto.UpdatedOwnerInfo;
import ru.ermolaayyyyyyy.leschats.entities.Owner;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    OwnerDto findOwnerById(int id);
    OwnerDto saveOwner(OwnerInfo ownerInfo);
    void deleteOwner(int id);
    OwnerDto updateOwner(UpdatedOwnerInfo ownerInfo);
    List<OwnerDto> findAllOwners(int cnt);
    Owner getOwnerById(int id);
}
