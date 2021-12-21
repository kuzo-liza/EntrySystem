package kuzoliza.entrySystem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByTelephoneNumber(String telephoneNumber);

    List<User> findAllByNameAndSurname(String name, String surname);

    List<User> findAllBySurname(String surname);

    List<User> findAllByInsideTrue();
}
