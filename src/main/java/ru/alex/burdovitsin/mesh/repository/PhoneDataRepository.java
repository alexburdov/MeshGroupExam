package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.burdovitsin.mesh.model.jpa.PhoneData;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    long countByPhone(String phoneNumber);
}
