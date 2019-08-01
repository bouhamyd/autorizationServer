package com.api.microservices.services;
import com.api.microservices.domaine.Authority;
import com.api.microservices.domaine.CustomUser;
import com.api.microservices.domaine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final AuthorityService authorityService;


    @Autowired
    public UserService(UserRepository userRepository, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        String[] authorities = user.getAuthorities().stream().map(Authority::getName).toArray(String[]::new);
        return new CustomUser(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(authorities));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        LOGGER.debug("Recherche de tous les utilisateurs");
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        LOGGER.debug("Recherche de l'utilisateur {}", username);
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Impossible de trouver l'utilisateur %s", username)));
    }
}