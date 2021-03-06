package graduation.project.controller;

import graduation.project.dto.AuthResponse;
import graduation.project.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtil;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthResponse authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }

        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
