package com.cfs.BookMyShow.Service;

import com.cfs.BookMyShow.Repostitry.UserRepository;
import com.cfs.BookMyShow.dto.UserDto;
import com.cfs.BookMyShow.exception.ResourceNotFound;
import com.cfs.BookMyShow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UseService {
    @Autowired
    private UserRepository userRepository;

    private UserDto createUser(UserDto userDto)
    {
        User user=mapToEntity(userDto);
        User savedUser=userRepository.save(user);
        return mapToDto(savedUser);
    }

    public UserDto getUserById(Long id)
    {
        User user=userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Use not found with id: "+id));
        return mapToDto(user);
    }

    public List<UserDto> getAllUsers()
    {
        List<User> users=userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //update user
    //delete user

    private User mapToEntity(UserDto userDto) {
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return  user;
    }

    private UserDto mapToDto(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;

    }
}
