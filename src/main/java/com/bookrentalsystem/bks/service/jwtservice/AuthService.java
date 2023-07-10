package com.bookrentalsystem.bks.service.jwtservice;

import com.bookrentalsystem.bks.dto.authorizationdto.AuthRequest;
import com.bookrentalsystem.bks.dto.authorizationdto.AuthResponse;
import com.bookrentalsystem.bks.exception.RestApiExceptionHandler.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    //this is autowired from the security config bean
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse generateToken(AuthRequest authRequest){
        //it is done because if the user is not in the database then we cannot give token to anyone
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            String jwtToken =   jwtService.generateToken(authRequest.getName());

            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        }else {
            throw new UserNotFoundException("User does not exist!!!");
        }

    }
}
