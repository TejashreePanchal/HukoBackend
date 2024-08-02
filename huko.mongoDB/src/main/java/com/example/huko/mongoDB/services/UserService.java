package com.example.huko.mongoDB.services;

import com.example.huko.mongoDB.models.UserModel;
import com.example.huko.mongoDB.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserModel addUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getAUser(String id) {
        return userRepository.findById(id);
    }

    public String authenticateUser(String email, String password) {
        List<UserModel> users = userRepository.findAll();
        logger.info("Authenticating user with email: " + email);
        for (UserModel user : users) {
            logger.info("Checking user: " + user.getEmail());
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // Generate token (for simplicity, use email as token)
                logger.info("User authenticated: " + user.getEmail());
                return generateToken(user);
            }
        }
        logger.warning("Authentication failed for email: " + email);
        return null;
    }

    private String generateToken(UserModel user) {
        // Implement token generation logic here (e.g., JWT)
        return user.getEmail(); // Simplified example
    }
}
