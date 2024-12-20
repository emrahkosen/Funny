package fun.funny.service;

import fun.funny.auth.UserDetailsImp;
import fun.funny.dto.AuthenticationRequest;
import fun.funny.dto.AuthenticationResponse;
import fun.funny.dto.RegisterRequest;
import fun.funny.auth.jwt.JwtService;
import fun.funny.entity.User;
import fun.funny.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUserName(), request.getPassword()
        ));
        User user = userRepository.findByUsername(request.getUserName()).orElseThrow();
        String token = jwtService.generateToken(new UserDetailsImp(user));
        return new AuthenticationResponse(token);
    }
}
