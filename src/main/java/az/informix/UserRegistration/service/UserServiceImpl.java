package az.informix.UserRegistration.service;

import az.informix.UserRegistration.model.UserDtls;
import az.informix.UserRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDtls createUser(UserDtls user) {
        return userRepository.save(user);
    }
}
