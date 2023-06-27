package ru.ermolaayyyyyyy.leschats.ownersMicroservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ermolaayyyyyyy.leschats.entities.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}

