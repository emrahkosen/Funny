package fun.funny.auth;

//import fun.funny.entity.User;
//import fun.funny.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        User user = userOptional.get();
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getRoles().stream()
//                    .map(role -> new SimpleGrantedAuthority(role.getName()))
//                    .collect(Collectors.toList())
//        );
//    }
//}