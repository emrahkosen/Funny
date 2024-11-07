package fun.funny.auth;

//import fun.funny.entity.Role;
//import fun.funny.entity.User;
//import fun.funny.repository.RoleRepository;
//import fun.funny.repository.UserRepository;
//import fun.funny.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//
//@Component
//public class DataInitializer {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private UserService userService;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Reuse instance
//
//    @PostConstruct
//    public void init() {
//        // Create and save roles
//        //Role adminRole = new Role();
//        //adminRole.setName("ADMIN");
//        //roleRepository.save(adminRole);
//
//        //Role basicUserRole = new Role();
//        //basicUserRole.setName("BASIC_USER");
//        //roleRepository.save(basicUserRole);
//
//        // Fetch roles back from the database (to ensure they are managed)
//        Role adminRole = roleRepository.findByName("ADMIN").get(); // Assuming you have this method
//        Role basicUserRole = roleRepository.findByName("BASIC_USER").get(); // Assuming you have this method
//
//        // Create and save users with roles
//        User adminUser = new User();
//        adminUser.setUsername("admin");
//        adminUser.setPassword(passwordEncoder.encode("adminpass"));
//        //adminUser.getRoles().add(adminRole);
//        userService.saveUser(adminUser);
//        userService.addUserRole(adminUser.getUserId(), adminRole.getId());
//
//
//        User basicUser = new User();
//        basicUser.setUsername("user");
//        basicUser.setPassword(passwordEncoder.encode("userpass"));
//        //basicUser.getRoles().add(basicUserRole);
//        //userRepository.save(basicUser);
//        userService.saveUser(basicUser);
//        userService.addUserRole(basicUser.getUserId(), basicUserRole.getId());
//    }
//}