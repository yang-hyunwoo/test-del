package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.domain.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByName(String name);

}
