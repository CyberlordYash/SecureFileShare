package in.yashsachan.SecureFileShare.service;


import in.yashsachan.SecureFileShare.model.Role;
import in.yashsachan.SecureFileShare.repository.RoleRepository;
import in.yashsachan.SecureFileShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import  in.yashsachan.SecureFileShare.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        // Convert each Role to a SimpleGrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());



        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public User registerUser(String username, String rawPassword, String roleName) {
        User existing = userRepository.findByUsername(username);
        if (existing != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        user.getRoles().add(role);
        userRepository.save(user);
        return user;
    }
}

