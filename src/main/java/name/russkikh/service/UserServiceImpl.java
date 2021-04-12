package name.russkikh.service;

import name.russkikh.dao.UserRepository;
import name.russkikh.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
