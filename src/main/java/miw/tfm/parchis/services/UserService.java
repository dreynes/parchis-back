package miw.tfm.parchis.services;

import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.mongo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existUser(UserModel user) {
        return userRepository.findByUsername(user.getUsername())!=null;
    }
    public UserEntity register(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(new UserEntity(user));
    }

    public boolean authenticate(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
