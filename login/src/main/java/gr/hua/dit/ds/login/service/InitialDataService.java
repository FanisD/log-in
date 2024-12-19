package gr.hua.dit.ds.login.service;

import com.github.javafaker.Faker;
import gr.hua.dit.ds.login.entity.*;
import gr.hua.dit.ds.login.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * Service to populate database with initial data.
 */
@Service
public class InitialDataService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataService(UserRepository userRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void createUsersAndRoles() {
        final List<String> rolesToCreate = List.of("ROLE_ADMIN", "ROLE_CREATOR", "ROLE_SUPPORTER");
        for (final String roleName : rolesToCreate) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                roleRepository.save(new Role(roleName));
                return null;
            });
        }

        this.userRepository.findByUsername("creator").orElseGet(() -> {
            User user = new User("creator", "creator@hua.gr", this.passwordEncoder.encode("1234"));
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByName("ROLE_CREATOR").orElseThrow());
            roles.add(this.roleRepository.findByName("ROLE_SUPPORTER").orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
            return null;
        });
        this.userRepository.findByUsername("supporter").orElseGet(() -> {
            User user = new User("supporter", "supporter@hua.gr", this.passwordEncoder.encode("1234"));
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByName("ROLE_CREATOR").orElseThrow());
            roles.add(this.roleRepository.findByName("ROLE_SUPPORTER").orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
            return null;
        });
        this.userRepository.findByUsername("admin").orElseGet(() -> {
            User user = new User("admin", "admin@hua.gr", this.passwordEncoder.encode("1234"));
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
            return null;
        });
    }

    @PostConstruct
    public void setup() {
        this.createUsersAndRoles();
    }
}
