package gr.hua.dit.ds.login.repository;

import gr.hua.dit.ds.login.entity.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Hidden
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
