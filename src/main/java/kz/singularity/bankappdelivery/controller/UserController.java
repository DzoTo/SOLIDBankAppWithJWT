package kz.singularity.bankappdelivery.controller;

import kz.singularity.bankappdelivery.dto.UserDTO;
import kz.singularity.bankappdelivery.model.request.LoginReq;
import kz.singularity.bankappdelivery.model.user.User;
import kz.singularity.bankappdelivery.service.impl.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Getter
@Setter
public class UserController {
    private final UserServiceImpl userService;


    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        List<User> users = userService.getAllUsers();
        List<UserDTO> userList = new ArrayList<>();
        for(User user:users){
            userList.add(new UserDTO(user));
        }
        return userList;
    }

    @GetMapping("/users/{user_id}")
    public UserDTO findUserById(@PathVariable("user_id") Long id){
        UserDTO user = new UserDTO( userService.getUserByID(id));
        return  user;
    }

    @PostMapping("/users")
    public void createUser(@RequestBody LoginReq loginReq){
        userService.createUser(loginReq.getUsername(), loginReq.getPassword());
    }

    @PutMapping("/update/{user_id}")
    public void updateUser(@PathVariable("user_id") Long id,
                           @RequestBody User user){
        userService.updateUser(id, user);
    }

    @DeleteMapping("/delete{user_id}")
    public void deleteUser(@PathVariable("user_id") Long id){
        userService.deleteUser(id);
    }

}
