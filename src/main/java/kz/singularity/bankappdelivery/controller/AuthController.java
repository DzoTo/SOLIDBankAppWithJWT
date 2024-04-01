package kz.singularity.bankappdelivery.controller;

import kz.singularity.bankappdelivery.auth.JwtUtil;
import kz.singularity.bankappdelivery.model.request.LoginReq;
import kz.singularity.bankappdelivery.model.response.ErrorRes;
import kz.singularity.bankappdelivery.model.response.LoginResponse;
import kz.singularity.bankappdelivery.model.user.User;
import kz.singularity.bankappdelivery.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody LoginReq registerRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());
        User registeredUser = new User(registerRequest.getUsername(), encodedPassword);
        userService.createUserWithReqBodyUser(registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    @ResponseBody
    @PostMapping( "/authenticate")
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            String username = authentication.getName();
            User user = new User(username,"");
            String token = jwtUtil.createToken(user);
            LoginResponse loginRes = new LoginResponse(username,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


}
