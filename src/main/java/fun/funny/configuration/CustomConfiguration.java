package fun.funny.configuration;

import fun.funny.entity.Role;
import fun.funny.entity.User;
import fun.funny.repository.RoleRepository;
import fun.funny.repository.UserRepository;
import fun.funny.service.RoleService;
import fun.funny.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Configuration
public class CustomConfiguration {

//    @Bean
//    CommandLineRunner run(UserService userService, RoleService roleService) {
//        return args -> {
//            // Check if roles exist before creating
//            if (!roleService.findByName("ADMIN").isPresent()) {
//                Role admin = new Role();
//                admin.setName("ADMIN");
//                roleService.saveRole(admin);
//            }
//            if (!roleService.findByName("SIMPLE").isPresent()) {
//                Role simple = new Role();
//                simple.setName("SIMPLE");
//                roleService.saveRole(simple);
//            }
//
//            // Create users if not exist
//            if (userService.findUserByUsername("emrah") != null) {
//                User user = new User();
//                user.setUsername("emrah");
//                user.setPassword("123");
//                user.setEmail("emrah@gmail.com");
//                user.setFirstName("emrah");
//                user.setLastName("emrah");
//                Optional<Role> optionalRole = roleService.findByName("ADMIN");
//                if (optionalRole.isPresent()) {
//                    user.setRoles(new HashSet<>(Arrays.asList(optionalRole.get())));
//                    userService.saveUser(user);
//                }
//            }
//
//            if (userService.findUserByUsername("jonh_doe") != null) {
//                User admin = new User();
//                admin.setUsername("jonh_doe");
//                admin.setPassword("qwe"); // Plain text password
//                admin.setRoles(new HashSet<>(Arrays.asList(roleService.findByName("SIMPLE").get())));
//                userService.saveUser(admin);
//            }
//        };
//    }


}
