package ru.ermolaayyyyyyy.leschats.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ermolaayyyyyyy.leschats.models.Color;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
public class Cat implements Serializable {

    private String name;
    private LocalDate birthDate;

    private String breed;
    private Color color;
    @ManyToOne
    @Setter(AccessLevel.PACKAGE)
    @JsonIgnore
    private Owner owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name="cat_cat",
            joinColumns = @JoinColumn(
                    name="cat_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="friends_id", referencedColumnName = "id"
            )
    )
    private List<Cat> friends;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Cat(String name, LocalDate birthDate, String breed, Color color, Owner owner){
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        friends = new ArrayList<>();
    }

    public void addFriend(Cat cat){
        friends.add(cat);
    }
    public void update(String name, LocalDate birthDate, String breed, Color color, Owner owner){
        if (name != null){
            setName(name);
        }
        if (birthDate != null){
            setBirthDate(birthDate);
        }
        if (breed != null){
            setBreed(breed);
        }
        if (color != null){
            setColor(color);
        }
        if (owner != null){
            setOwner(owner);
        }
    }
}
