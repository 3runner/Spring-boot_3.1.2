package name.russkikh.dao;

import name.russkikh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String username);

}
