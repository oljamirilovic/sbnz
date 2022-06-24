package sbnz.integracija.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sbnz.integracija.example.dto.LoginDTO;
import sbnz.integracija.example.dto.TokenDTO;
import sbnz.integracija.example.exception.BadRequestException;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.security.TokenUtils;
import sbnz.integracija.example.service.UserDetailsService;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            TokenUtils tokenUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity loginUser(@RequestBody LoginDTO login) {
        try {
            TokenDTO token = new TokenDTO();
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(login.getUsername());
            String tokenValue = this.tokenUtils.generateToken(userDetails);
            token.setToken(tokenValue);
            token.setUsername(login.getUsername());
            token.setUserType(this.userDetailsService.findRoleByUsername(login.getUsername()));

            //Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

            final Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        login.getUsername(), login.getPassword()));
            } catch (BadCredentialsException e) {
                return  new ResponseEntity<>("Your credentials are bad. Please, try again", HttpStatus.BAD_REQUEST);
            } catch (NotFoundException e){
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Wrong password!", HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e){
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        } catch(BadRequestException e){
            return new ResponseEntity<>("Unauthorized.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(
            value = "/logout",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity logoutUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            throw new BadRequestException("User is not authenticated!");
        }

    }
}
