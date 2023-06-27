//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.ermolaayyyyyyy.leschats.chatsMicroservices.CatRepository;
//import ru.ermolaayyyyyyy.leschats.chatsMicroservices.CatServiceImpl;
//import ru.ermolaayyyyyyy.leschats.dto.CatDto;
//import ru.ermolaayyyyyyy.leschats.dto.ControllerCatDto;
//import ru.ermolaayyyyyyy.leschats.dto.FriendshipDto;
//import ru.ermolaayyyyyyy.leschats.entities.Cat;
//import ru.ermolaayyyyyyy.leschats.entities.Owner;
//import ru.ermolaayyyyyyy.leschats.exceptions.InvalidAttributeException;
//import ru.ermolaayyyyyyy.leschats.models.Color;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@SpringBootTest(classes = ChatsMicroserviceTestConfig.class)
//public class ChatsMicroserviceTest {
//    @Mock
//    private CatRepository catRepository;
//    private CatServiceImpl catService;
//
//    @BeforeEach
//    void returnCat(){
//        catService = new CatServiceImpl(catRepository);
//        Owner owner = new Owner("Owner", LocalDate.now());
//        Cat cat = new Cat("Cat", LocalDate.now(), "Breed", Color.BROWN, owner);
//        Mockito.when(catRepository.findById(1)).thenReturn(Optional.of(cat));
//    }
//
//    @Test
//    void findCat_validAttributes() {
//        CatDto catDto = catService.findCatById(1);
//        Assertions.assertEquals("Cat", catDto.name());
//    }
//
//    @Test
//    void addFriendship_catsAreFriends_AddFriendshipAgain_throwException() {
//        Owner owner2 = new Owner("Owner2", LocalDate.now());
//        Cat cat2 = new Cat("Cat2", LocalDate.now(), "Breed2", Color.RED, owner2);
//        Mockito.when(catRepository.findById(2)).thenReturn(Optional.of(cat2));
//        catService.addFriend(new FriendshipDto(1, 2));
//        Assertions.assertTrue(catService.findCatById(1).friends().contains(cat2.getId()));
//        Assertions.assertThrows(InvalidAttributeException.class, () -> catService.addFriend(new FriendshipDto(2, 1)));
//    }
//
//    @Test
//    void tryToAddRoseCat_throwException() {
//        var catDto = new ControllerCatDto("Cat", LocalDate.now(), "Breed", "ROSE", 1);
//        Assertions.assertThrows(InvalidAttributeException.class, () -> catService.saveCat(catDto));
//    }
//}
