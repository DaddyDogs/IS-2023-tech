package ru.ermolaayyyyyyy.leschats.servicelayer.services.implementations;

import lombok.experimental.ExtensionMethod;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ermolaayyyyyyy.leschats.dto.OwnerInfo;
import ru.ermolaayyyyyyy.leschats.entities.Owner;
import ru.ermolaayyyyyyy.leschats.entities.User;
import ru.ermolaayyyyyyy.leschats.exceptions.RabbitException;
import ru.ermolaayyyyyyy.leschats.models.Role;
import ru.ermolaayyyyyyy.leschats.dataaccesslayer.repositories.UserRepository;
import ru.ermolaayyyyyyy.leschats.dto.OwnerDto;
import ru.ermolaayyyyyyy.leschats.dto.UserDto;
import ru.ermolaayyyyyyy.leschats.exceptions.EntityNotFoundException;
import ru.ermolaayyyyyyy.leschats.exceptions.InvalidAttributeException;
import ru.ermolaayyyyyyy.leschats.mapping.UserDtoMapping;

import java.time.LocalDate;
import java.util.List;

@ExtensionMethod(UserDtoMapping.class)
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AmqpTemplate template;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto saveUser(String login, String password, String name, LocalDate birthdate, String role) {
        if (userRepository.findByLogin(login) != null) {
            throw InvalidAttributeException.loginAlreadyExist(login);
        }

        //OwnerDto ownerDto = ownerService.saveOwner(name, birthdate);
        OwnerDto ownerDto = (OwnerDto) template.convertSendAndReceive("saveOwner", new OwnerInfo(name, birthdate));
        checkForAnswer(ownerDto, "saveOwner");
        Role role1 = getRole(role);
        //User user = new User(ownerService.getOwnerById(ownerDto.id()), login, bCryptPasswordEncoder.encode(password), role1);
        Owner owner = (Owner) template.convertSendAndReceive("getOwner", ownerDto.id());
        checkForAnswer(owner, "getOwner");
        User user = new User(owner, login, bCryptPasswordEncoder.encode(password), role1);
        userRepository.saveAndFlush(user);
        return user.asDto(name, birthdate);
    }
    public void deleteUser(int id) {
        User user = getUserById(id);
        template.convertAndSend("deleteOwner", user.getOwner().getId());
        userRepository.deleteById(id);
    }

    public UserDto findUserById(int id) {
        User user = getUserById(id);
        return user.asDto(user.getOwner().getName(), user.getOwner().getBirthDate());
    }
    public UserDto updateUser(String password, String login, String name, LocalDate birthDate, int id) {
        User user = userRepository.findByLogin(login);
        User user2 = getUserById(id);
        if (user != null && user.getId() != id) {
            throw InvalidAttributeException.loginAlreadyExist(login);
        }

        user2.update(login, bCryptPasswordEncoder.encode(password));
        userRepository.save(user2);
        Owner owner = (Owner) template.convertSendAndReceive("getOwner", user2.getOwner().getId());
        //ownerService.getOwnerById(user2.getOwner().getId()).update(name, birthDate);
        checkForAnswer(owner, "getOwner");
        owner.update(name, birthDate);

        return user2.asDto(user2.getOwner().getName(), user2.getOwner().getBirthDate());
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(x -> x.asDto(x.getOwner().getName(), x.getOwner().getBirthDate())).toList();
    }
    @Override
    @RabbitListener(queues = "authenticate")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null){
            throw EntityNotFoundException.userDoesNotExist(username);
        }
        return user;
    }

    public String getUsernameByOwnerId(int id){
        User user = userRepository.findByOwnerId(id);
        if (user == null){
            throw EntityNotFoundException.ownerDoesNotExist(id);
        }
        return user.getUsername();
    }
    private User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> EntityNotFoundException.userDoesNotExist(id));
    }
    private Role getRole(String role){
        Role roleEnum = Role.findRole(role);
        if (roleEnum == null){
            throw InvalidAttributeException.roleDoesNotExist(role);
        }
        return roleEnum;
    }
    private <T> void checkForAnswer(T entity, String queueName){
        if (entity == null){
            throw RabbitException.GatewayTimeoutException(queueName);
        }
    }
}
