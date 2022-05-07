package tacocloud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// Spring Security provides two main interfaces:
// 1 UserDetailsService An interface that let you provide UserDetails to the security context,
// providing a custom implementation for loadUserByUsername(String userName).
public interface UserDetailsService {
//  2 UserDetails содержит информацию о реальном пользователе
//  username, password, account status, and the roles
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

