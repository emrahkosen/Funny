package fun.funny.configuration;

//import fun.funny.dto.UserDTO;
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
////        Role adminRole = roleRepository.findByName("ADMIN").get(); // Assuming you have this method
//
//        // Create and save users with roles
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername("ekosen");
//        userDTO.setPassword("123");
//        //adminUser.getRoles().add(adminRole);
//        userService.createUser(userDTO);
////        userService.addUserRole(userDTO.getUserId(), adminRole.getId());
//
//    }
//}