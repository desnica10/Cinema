package com.danilo.cinema.service;

import com.danilo.cinema.dto.UserDTO;
import com.danilo.cinema.model.User;
import com.danilo.cinema.repository.UserRepository;
import com.danilo.cinema.util.UserType;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    DozerBeanMapper mapper = new DozerBeanMapper();

    @Autowired
    UserRepository userRepository;

    public UserDTO createCustomer(UserDTO request) {

        if (userRepository.findByUsername(request.getUsername()) != null){
            return null;
        }

        User user = mapper.map(request, User.class);
        user.setActive(true);
        user.setType(UserType.CUSTOMER);

        userRepository.save(user);

        return mapper.map(user, UserDTO.class);
    }

    public UserDTO findUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null){
            return null;
        }

        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }

    public UserDTO findUserById(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return null;
        }

        return mapper.map(user, UserDTO.class);
    }

    public UserDTO updateUser(Long userId, UserDTO request) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return null;
        }

        if (userRepository.findByUsername(request.getUsername()) != null && !user.getUsername().equals(request.getUsername())){
            return null;
        }

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setBirth(request.getBirth());

        userRepository.save(user);

        return mapper.map(user, UserDTO.class);
    }

    public boolean isUserActive(UserDTO request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null || user.isActive() == false){
            return false;
        }

        return true;
    }

    public boolean changePassword(Long userId, UserDTO request) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return false;
        }

        user.setPassword(request.getPassword());
        userRepository.save(user);

        return true;
    }

    public List<UserDTO> findActiveManagers() {

        List<User> users = userRepository.findAllByTypeAndActive(UserType.MANAGER, true);

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user: users) {
            userDTOs.add(mapper.map(user, UserDTO.class));
        }

        return userDTOs;
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user: users) {
            userDTOs.add(mapper.map(user, UserDTO.class));
        }

        return userDTOs;
    }

    public List<UserDTO> changeUserActivity(Long userId, boolean active) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return null;
        }

        user.setActive(active);

        userRepository.save(user);

        return findAllUsers();
    }

    public List<UserDTO> changeUserType(Long userId, UserType type) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null){
            return null;
        }

        user.setType(type);

        userRepository.save(user);

        return findAllUsers();
    }
}
