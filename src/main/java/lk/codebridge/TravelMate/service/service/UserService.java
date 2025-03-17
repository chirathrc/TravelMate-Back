package lk.codebridge.TravelMate.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lk.codebridge.TravelMate.entity.User;

@Service
public interface UserService {

    public boolean registerUser(User user);

    public User getUserByEmail(String email);

    public User getUserByMobile(String mobile);

    public User getUserByEmail(String email,String password);

    public User getUserByMobile(String mobile, String password);

    public List<User> getAllUsers();

    public boolean updateUser(User user);

    public boolean deleteUser(int id);

}
