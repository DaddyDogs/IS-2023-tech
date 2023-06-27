package ru.ermolaayyyyyyy.leschats.presentation.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ermolaayyyyyyy.leschats.dto.ExceptionMessage;
import ru.ermolaayyyyyyy.leschats.presentation.dto.ControllerUserDto;
import ru.ermolaayyyyyyy.leschats.dto.UserDto;
import ru.ermolaayyyyyyy.leschats.exceptions.AccessDeniedException;
import ru.ermolaayyyyyyy.leschats.servicelayer.services.implementations.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@SecurityRequirement(name="cats-api")
public class UserServiceController {
    private final UserService userService;
    Logger logger = LogManager.getLogger(UserServiceController.class);
    @Autowired
    AmqpTemplate template;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody ControllerUserDto userDto) {
        UserDto userDtoSaved = userService.saveUser(userDto.login(), userDto.password(), userDto.name(), userDto.birthDate(), userDto.role());
        return new ResponseEntity<>(userDtoSaved, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@PathVariable @Min(1) int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User was deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody ControllerUserDto userDto, @PathVariable @Min(1) int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username =((UserDetails) principal).getUsername();
            if (!Objects.equals(username, userDto.login())) {
                throw AccessDeniedException.noAccessForUserException(username);
            }
        }
        else{
            throw AccessDeniedException.notAuthorizedException();
        }
        UserDto updatedUserDto = userService.updateUser(userDto.password(), userDto.login(), userDto.name(), userDto.birthDate(), id);
        return new ResponseEntity<>(updatedUserDto, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable @Min(1) int id) {
        UserDto userDto = userService.findUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
