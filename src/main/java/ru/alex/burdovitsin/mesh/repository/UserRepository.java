package ru.alex.burdovitsin.mesh.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.alex.burdovitsin.mesh.model.jpa.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u " +
            " LEFT JOIN EmailData as ed ON u.id = ed.userId " +
            " LEFT JOIN PhoneData as pd ON u.id = pd.userId " +
            " WHERE u.username LIKE :userNameStartFrom% " +
            " AND (:dateOfBirdFrom = null OR u.dateOfBird > :dateOfBirdFrom) " +
            " AND pd.phone LIKE :phoneNumber " +
            " AND ed.email LIKE :email")
    List<User> getUserWithParameters(@Param("userNameStartFrom") String userNameStartFrom,
                                     @Param("dateOfBirdFrom") LocalDate dateOfBirdFrom,
                                     @Param("email") String email,
                                     @Param("phoneNumber") String phoneNumber,
                                     Pageable pageable);

    @Query("SELECT u FROM User u " +
            " LEFT JOIN Account ac ON u.id = ac.userId " +
            " WHERE ac.initBalance * 207 < ac.balance")
    List<User> getUsersForIncreaseBalance();
}
