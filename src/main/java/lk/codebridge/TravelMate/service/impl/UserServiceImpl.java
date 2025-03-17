package lk.codebridge.TravelMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.User;
import lk.codebridge.TravelMate.repository.UserRepository;
import lk.codebridge.TravelMate.service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean registerUser(User user) {

        User checkUser = userRepository.findByEmail(user.getEmail());

        if (checkUser != null) {

            return false;
        } else {

            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;

        }

    }

    @Override
    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByMobile(String mobile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByMobile'");
    }

    @Override
    public User getUserByEmail(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByMobile(String mobile, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByMobile'");
    }

    @Override
    public List<User> getAllUsers() {
   
        return userRepository.findAll();
    }

    @Override
    public boolean updateUser(User user) {
        
        User checkUser = userRepository.findByEmail(user.getEmail());

        if (checkUser != null) {

            userRepository.save(user);
            return true;
        } else {

            return false;

        }
    }

    @Override
    public boolean deleteUser(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

}
