package pl.czajkowski.traintogether.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.auth.models.LoginRequest;
import pl.czajkowski.traintogether.auth.models.LoginResponse;
import pl.czajkowski.traintogether.security.jwt.JwtService;
import pl.czajkowski.traintogether.user.UserMapper;
import pl.czajkowski.traintogether.user.models.User;
import pl.czajkowski.traintogether.user.models.UserDTO;

@Service
public class AuthService {

    private final JwtService service;

    private final UserDetailsService userService;

    private final UserMapper mapper;

    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService service,
                       UserDetailsService userService,
                       UserMapper mapper,
                       AuthenticationManager authenticationManager) {
        this.service = service;
        this.userService = userService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        return new LoginResponse(
                mapper.toUserDTO((User)userService.loadUserByUsername(request.username())),
                service.generateToken(auth)
        );
    }

    public UserDTO authenticate(String username) {
        return mapper.toUserDTO((User) userService.loadUserByUsername(username));
    }
}
