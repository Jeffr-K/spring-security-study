package lambsroad.springsecuritystudy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
      .name(request.getName())
      .pw(passwordEncoder.encode(request.getPw()))
      .email(request.getEmail())
      .role(Role.USER)
      .build();
    userRepository.save(user);
    var token = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
      .token(token)
      .build();
  }

  public AuthenticationResponse authenticate(RequestLogin request) {
    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPw()));
    var user = this.userRepository.findByEmail(request.getEmail()).orElseThrow();
    var token = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
      .token(token)
      .build();
  }
}
