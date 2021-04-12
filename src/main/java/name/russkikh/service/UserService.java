package name.russkikh.service;

import name.russkikh.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User getOne(long id);

    Optional<User> findById(long id);

    Optional<User> findUserByName(String username);

    void save(User user);

    void deleteById(long id);
}