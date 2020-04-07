package com.project.isa.controller;


import com.project.isa.dto.LoginDTO;
import com.project.isa.dto.PatientDTO;
import com.project.isa.exceptions.EntityAlreadyExistsException;
import com.project.isa.exceptions.InvalidDataException;
import com.project.isa.security.TokenUtils;
import com.project.isa.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    TokenUtils tokenUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        try{
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(), loginDTO.getPassword());

            //System.out.println("password" + loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            //System.out.println("details");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //System.out.println(authentication.getAuthorities());

            // Reload user details so we can generate token
            UserDetails details = userDetailsService.
                    loadUserByUsername(loginDTO.getUsername());

            return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register (@RequestBody PatientDTO patientDTO){

        try{
            userService.sendRegistrationRequest(patientDTO);
        } catch (InvalidDataException | EntityAlreadyExistsException e){
            return new ResponseEntity<String>("Invalid registration request" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successful registration request", HttpStatus.OK);
    }



























}
