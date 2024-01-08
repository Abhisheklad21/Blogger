package com.blogger.controller;

import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.payload.LoginDto;
import com.blogger.payload.SignUpDto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@Api(value = "/api", tags ="Blogger")
public class AuthController {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    // IN SIGN IN .authenticate CALLS THE loadByUsername METHOD IN THE CUSTOMUSERDETIALSERVICE CLASS TO AUTHENTICATE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in Successfully !", HttpStatus.OK);
    }


    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        if (userRepo.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email id exists :" + signUpDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepo.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username  exists :" + signUpDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepo.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(role));

        User savedUser = userRepo.save(user);
        return new ResponseEntity<>("User Registered Successfully !", HttpStatus.OK);

    }
}
