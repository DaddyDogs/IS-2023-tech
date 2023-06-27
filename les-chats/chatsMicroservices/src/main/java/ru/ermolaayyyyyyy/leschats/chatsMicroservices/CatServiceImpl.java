package ru.ermolaayyyyyyy.leschats.chatsMicroservices;

import lombok.experimental.ExtensionMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ermolaayyyyyyy.leschats.dto.ControllerCatDto;
import ru.ermolaayyyyyyy.leschats.dto.ExceptionMessage;
import ru.ermolaayyyyyyy.leschats.dto.FriendshipDto;
import ru.ermolaayyyyyyy.leschats.entities.Cat;
import ru.ermolaayyyyyyy.leschats.entities.Owner;
import ru.ermolaayyyyyyy.leschats.exceptions.RabbitException;
import ru.ermolaayyyyyyy.leschats.models.Color;
import ru.ermolaayyyyyyy.leschats.chatsMicroservices.CatRepository;
import ru.ermolaayyyyyyy.leschats.dto.CatDto;
import ru.ermolaayyyyyyy.leschats.exceptions.EntityNotFoundException;
import ru.ermolaayyyyyyy.leschats.exceptions.InvalidAttributeException;
import ru.ermolaayyyyyyy.leschats.mapping.CatDtoMapping;
import ru.ermolaayyyyyyy.leschats.chatsMicroservices.CatService;


import java.time.LocalDate;
import java.util.List;
@ComponentScan("ru.ermolaayyyyyyy.leschats.chatsMicroservices")
@ExtensionMethod(CatDtoMapping.class)
@Service
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;
    @Autowired
    AmqpTemplate template;
    Logger logger = LogManager.getLogger(CatServiceImpl.class);
    @Autowired
    public CatServiceImpl(CatRepository catRepository){
        this.catRepository = catRepository;
    }

    @RabbitListener(queues = "findCat")
    @Override
    public CatDto findCatById(int id) {
        return getCatById(id).asDto();
    }

    @RabbitListener(queues = "saveCat")
    @Override
    public CatDto saveCat(ControllerCatDto catDto) {
        logger.info("Emit to saveCat queue");
        Color colorEnum = getColor(catDto.color());
        Owner owner = getOwnerById(catDto.ownerId());
        Cat cat = new Cat(catDto.name(), catDto.birthDate(), catDto.breed(), colorEnum, owner);
        catRepository.saveAndFlush(cat);
        owner.addCat(cat);
        return cat.asDto();
    }

    @RabbitListener(queues = "deleteCat")
    @Override
    public void deleteCat(int id) {
        logger.info("Emit to deleteCat queue");
        Cat cat = getCatById(id);
        for (Cat friend : cat.getFriends()){
            friend.getFriends().remove(cat);
            catRepository.save(friend);
        }
        catRepository.deleteById(id);
    }

    @Override
    public CatDto updateCat(String name, LocalDate birthDate, String breed, String color, int ownerId, int id) {
        Owner owner = getOwnerById(ownerId);
        Color colorEnum = getColor(color);
        Cat cat = getCatById(id);
        cat.update(name, birthDate, breed, colorEnum, owner);
        catRepository.save(cat);
        return cat.asDto();
    }

    @RabbitListener(queues = "findAllCats")
    @Override
    public List<CatDto> findAllCats(int count) {
        logger.info("Emit to findAllCats queue");
        return catRepository.findAll().stream().map(x -> x.asDto()).toList();
    }

    @RabbitListener(queues = "findFilteredCats")
    @Override
    public List<CatDto> findFilteredCats(Specification<Cat> spec) {
        logger.info("Emit to findFilteredCats queue");
        return catRepository.findAll(spec).stream().map(CatDtoMapping::asDto).toList();
    }

    @RabbitListener(queues = "addFriend", returnExceptions = "true", errorHandler = "customRabbitListenerErrorHandler")
    @Override
    public String addFriend(FriendshipDto friendsId) {
        logger.info("Emit to addFriend queue");
        Cat cat1 = getCatById(friendsId.id1());
        Cat cat2 = getCatById(friendsId.id2());
        for (Cat friend : cat1.getFriends()){
            if (friend.getId() == cat2.getId()) {
                throw InvalidAttributeException.friendAlreadyExist(friendsId.id1(), friendsId.id2());
            }
        }
        cat1.addFriend(cat2);
        cat2.addFriend(cat1);
        catRepository.save(cat1);
        catRepository.save(cat2);
        return "Ok";
    }

    private Color getColor(String color){
        Color colorEnum = Color.findColor(color);
        if (colorEnum == null){
            throw InvalidAttributeException.colorDoesNotExist(color);
        }
        return colorEnum;
    }

    private Cat getCatById(int id){
        return catRepository.findById(id).orElseThrow(() -> EntityNotFoundException.catDoesNotExist(id));
    }

    private Owner getOwnerById(int id){
        Owner owner = (Owner) template.convertSendAndReceive("getOwner", id);
        if (owner == null){
            throw RabbitException.GatewayTimeoutException("getOwner");
        }
        return owner;
    }
}
