package org.authservice.service;

import org.authservice.model.dto.*;

public interface AuthenticationService {

    SignUpResponse signUp(UserCredentialsDto request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
