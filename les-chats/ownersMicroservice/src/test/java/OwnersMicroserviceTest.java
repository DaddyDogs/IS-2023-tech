import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ermolaayyyyyyy.leschats.dto.OwnerDto;
import ru.ermolaayyyyyyy.leschats.dto.OwnerInfo;
import ru.ermolaayyyyyyy.leschats.dto.UpdatedOwnerInfo;
import ru.ermolaayyyyyyy.leschats.exceptions.EntityNotFoundException;
import ru.ermolaayyyyyyy.leschats.ownersMicroservice.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ermolaayyyyyyy.leschats.ownersMicroservice.OwnerService;
import ru.ermolaayyyyyyy.leschats.ownersMicroservice.OwnerServiceImpl;

import java.time.LocalDate;

@SpringBootTest(classes = OwnersMicroserviceTestConfig.class)
public class OwnersMicroserviceTest {
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void addOwner_validAttributes() {
        OwnerService ownerService = new OwnerServiceImpl(ownerRepository);
        LocalDate birthDate = LocalDate.of(2000, 4, 15);
        OwnerDto ownerDto = ownerService.saveOwner(new OwnerInfo("Roman", birthDate));
        OwnerDto owner = ownerService.findOwnerById(ownerDto.id());
        Assertions.assertEquals("Roman", owner.name());
        Assertions.assertEquals(birthDate, owner.birthDate());
    }

    @Test
    void deleteOwner_findDeletedOwner_throwException() {
        OwnerService ownerService = new OwnerServiceImpl(ownerRepository);
        LocalDate birthDate = LocalDate.of(2000, 4, 15);
        OwnerDto ownerDto = ownerService.saveOwner(new OwnerInfo("Roman", birthDate));
        ownerService.deleteOwner(ownerDto.id());
        Assertions.assertThrows(EntityNotFoundException.class, () -> ownerService.findOwnerById(ownerDto.id()));
    }

    @Test
    void updateOwner_nameWasChanged() {
        OwnerService ownerService = new OwnerServiceImpl(ownerRepository);
        LocalDate birthDate = LocalDate.of(2000, 4, 15);
        OwnerDto ownerDto = ownerService.saveOwner(new OwnerInfo("Roman", birthDate));
        OwnerDto owner = ownerService.findOwnerById(ownerDto.id());
        Assertions.assertEquals("Roman", owner.name());
        ownerService.updateOwner(new UpdatedOwnerInfo("Aleksandr", null, ownerDto.id()));
        Assertions.assertEquals("Aleksandr", ownerService.findOwnerById(ownerDto.id()).name());
    }
}
