package com.capstone.gym_workout_companion.config;

import com.capstone.gym_workout_companion.model.Role;
import com.capstone.gym_workout_companion.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
The UserPrincipal class is used to represent the authenticated user in the Spring Security context.
The UserPrincipal will hold the user's details, including their roles,
and allow Spring Security to manage authentication and authorization.
*/

public class UserPrincipal implements UserDetails {

    private User user;
    private List<Role> roles;

    // Constructor to initialize the UserPrincipal with a User object and their roles
    public UserPrincipal(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    // Returns a collection of authorities (roles) for the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // Returns the username (email or other unique identifier)
    @Override
    public String getUsername() {
        return user.getEmail(); // Assuming email is the unique identifier
    }

    // Returns the password
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Indicates whether the account is expired
    @Override
    public boolean isAccountNonExpired() {
        return true;  // Customize as needed
    }

    // Indicates whether the account is locked
    @Override
    public boolean isAccountNonLocked() {
        return true;  // Customize as needed
    }

    // Indicates whether the credentials (password) are expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Customize as needed
    }

    // Indicates whether the account is enabled
    @Override
    public boolean isEnabled() {
        return true;  // Customize as needed
    }

    // Getter for the User object
    public User getUser() {
        return user;
    }

    // Getter for the roles list
    public List<Role> getRoles() {
        return roles;
    }

    // Factory method to create a UserPrincipal from a User object and their roles
    public static UserPrincipal create(User user, List<Role> roles) {
        return new UserPrincipal(user, roles);
    }
}