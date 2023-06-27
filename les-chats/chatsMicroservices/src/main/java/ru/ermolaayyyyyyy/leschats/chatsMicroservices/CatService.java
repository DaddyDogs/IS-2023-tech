package ru.ermolaayyyyyyy.leschats.chatsMicroservices;

import org.springframework.data.jpa.domain.Specification;
import ru.ermolaayyyyyyy.leschats.dto.ControllerCatDto;
import ru.ermolaayyyyyyy.leschats.dto.FriendshipDto;
import ru.ermolaayyyyyyy.leschats.entities.Cat;
import ru.ermolaayyyyyyy.leschats.dto.CatDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CatService {
    CatDto findCatById(int id);
    CatDto saveCat(ControllerCatDto catDto);
    void deleteCat(int id);
    CatDto updateCat(String name, LocalDate birthDate, String breed, String color, int ownerId, int id);
    List<CatDto> findAllCats(int count);
    List<CatDto> findFilteredCats(Specification<Cat> spec);
    String addFriend(FriendshipDto friendsId);
}
