package ru.ermolaayyyyyyy.leschats.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "Owner")
public class Owner implements Serializable {
    private String name;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Cat> cats;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Owner(String name, LocalDate birthDate){
        this.name = name;
        this.birthDate = birthDate;
        cats = new ArrayList<>();
    }

    public void addCat(Cat cat){
        cats.add(cat);
    }
    public void update(String name, LocalDate birthDate){
        if (name != null){
            setName(name);
        }
        if (birthDate != null){
            setBirthDate(birthDate);
        }
    }

    public void deleteCat(Cat cat){
        cat.setOwner(null);
        cats.remove(cat);
    }
}
