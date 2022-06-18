package sbnz.integracija.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sbnz.integracija.example.exception.NotFoundException;
import sbnz.integracija.example.model.User;
import sbnz.integracija.example.model.UserRole;
import sbnz.integracija.example.repository.UserRepository;
import sbnz.integracija.example.repository.UserRoleRepository;
import sbnz.integracija.example.security.UserFactory;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user =  this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username '%s' is not found!", username)));

        return UserFactory.create(user);
    }

    public String findRoleByUsername(String username){
        Optional<UserRole> ret = userRoleRepository.findByUsername(username);
        if(ret.isPresent()){
            return ret.get().getName();
        }
        return "";
    }


}
