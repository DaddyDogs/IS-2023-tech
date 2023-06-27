package ru.ermolaayyyyyyy.leschats.chatsMicroservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.ermolaayyyyyyy.leschats.entities.Cat;
import ru.ermolaayyyyyyy.leschats.models.Color;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer>, JpaSpecificationExecutor<Cat> {
    List<Cat> findByColor(Color color);
    List<Cat> findByBreed(String breed);
    List<Cat> findByOwnerId(int id);
    List<Cat> findByBirthDate(LocalDate date);
}
