package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.repository.CrudRepository;
import ru.alex.burdovitsin.mesh.model.jpa.PhoneData;

public interface PhoneDataRepository extends CrudRepository<PhoneData, Long> {
    long countByPhone(String phoneNumber);
}
