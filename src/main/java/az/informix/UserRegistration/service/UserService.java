package az.informix.UserRegistration.service;

import az.informix.UserRegistration.model.UserDtls;

public interface UserService {
    public boolean checkEmail(String email);
    public UserDtls createUser(UserDtls user);
}
