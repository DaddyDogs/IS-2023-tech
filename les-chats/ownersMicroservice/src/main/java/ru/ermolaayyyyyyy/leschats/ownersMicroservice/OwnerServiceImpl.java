package ru.ermolaayyyyyyy.leschats.ownersMicroservice;

import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ermolaayyyyyyy.leschats.dto.OwnerInfo;
import ru.ermolaayyyyyyy.leschats.dto.UpdatedOwnerInfo;
import ru.ermolaayyyyyyy.leschats.entities.Cat;
import ru.ermolaayyyyyyy.leschats.entities.Owner;
import ru.ermolaayyyyyyy.leschats.dto.OwnerDto;
import ru.ermolaayyyyyyy.leschats.exceptions.EntityNotFoundException;
import ru.ermolaayyyyyyy.leschats.mapping.OwnerDtoMapping;

import java.util.List;

@ExtensionMethod(OwnerDtoMapping.class)
@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    AmqpTemplate template;
    private final OwnerRepository ownerRepository;
    Logger logger = LogManager.getLogger(OwnerServiceImpl.class);

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository){
        this.ownerRepository = ownerRepository;
    }
    @RabbitListener(queues = "findOwner")
    @Override
    public OwnerDto findOwnerById(int id) {
        return getOwnerById(id).asDto();
    }

    @RabbitListener(queues = "saveOwner")
    @Override
    public OwnerDto saveOwner(OwnerInfo ownerInfo) {
        Owner owner = new Owner(ownerInfo.name(), ownerInfo.birthdate());
        ownerRepository.saveAndFlush(owner);
        return owner.asDto();
    }

    @RabbitListener(queues = "deleteOwner")
    @Override
    public void deleteOwner(int id) {
        Owner owner = getOwnerById(id);
        for (Cat cat : owner.getCats()){
            template.convertAndSend("deleteCat", cat.getId());
        }
        logger.info("Send to deleteCat queue");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(!getOwnerById(id).getCats().isEmpty()){
            logger.info("Wait for deleting");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("Cats was deleted");
        ownerRepository.delete(getOwnerById(id));
    }

    @RabbitListener(queues = "updateOwner")
    @Override
    public OwnerDto updateOwner(UpdatedOwnerInfo ownerInfo) {
        Owner owner = getOwnerById(ownerInfo.id());
        owner.update(ownerInfo.name(), ownerInfo.birthdate());
        ownerRepository.save(owner);
        return owner.asDto();
    }

    @RabbitListener(queues = "findAllOwners")
    @Override
    public List<OwnerDto> findAllOwners(int cnt) {
        return ownerRepository.findAll().stream().map(x -> x.asDto()).toList();
    }

    @RabbitListener(queues = "getOwner")
    @Override
    public Owner getOwnerById(int id){
        logger.info("Emit to getOwner queue");
        return ownerRepository.findById(id).orElseThrow(() -> EntityNotFoundException.ownerDoesNotExist(id));
    }
}
