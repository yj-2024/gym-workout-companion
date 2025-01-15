package com.capstone.gym_workout_companion.service;

import com.capstone.gym_workout_companion.model.Role;
import com.capstone.gym_workout_companion.model.User;
import com.capstone.gym_workout_companion.repository.RoleRepository;
import com.capstone.gym_workout_companion.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j // annotation for logging
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, @Lazy
    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;

    }
    // Create new user with encrypted password
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Password hashing
        Role role = roleRepository.findById(1L).orElse(null);
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user details
    // Transactional ensures that database transactions are handled properly
    // (i.e., the changes are committed or rolled back if something goes wrong).
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            // Update other fields as needed
            return userRepository.save(user);
        }
        return null; // If user doesn't exist, return null or throw an exception
    }

    // Delete user by ID
    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false; // If user doesn't exist, return false
    }

    // Get user by email (to validate login or other purposes)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Check if email already exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
}

    // Validate user password  (using BCrypt) (could be used in login)
    public boolean validateUserPassword(User user, String password) {
    // Add password validation logic here, for example using BCrypt password encoder
        return user.getPassword().equals(password); // Use a password encoder for real-world apps
    }

    // Change user password
    @Transactional
    public boolean changeUserPassword(Long id, String newPassword) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false; // If user doesn't exist
    }

// Custom method to handle login (for example, validating username and password)
public boolean loginUser(String email, String password) {
    User user = getUserByEmail(email);
    if (user != null && validateUserPassword(user, password)) {
        return true;
    }
    return false;
}

// Count number of users (useful for admin dashboards or statistics)
public long getUserCount() {
    return userRepository.count();
}


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        log.info("User found: {}", user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
