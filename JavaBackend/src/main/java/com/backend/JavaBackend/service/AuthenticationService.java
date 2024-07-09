package com.backend.JavaBackend.service;


import com.backend.JavaBackend.dto.request.AuthenticationRequest;
import com.backend.JavaBackend.dto.request.IntrospectRequest;
import com.backend.JavaBackend.dto.response.AuthenticationResponse;
import com.backend.JavaBackend.dto.response.IntrospectResponse;
import com.backend.JavaBackend.entity.User;
import com.backend.JavaBackend.exception.AppException;
import com.backend.JavaBackend.exception.ErrorCode;
import com.backend.JavaBackend.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}") // read a variable from .yml file
    protected String signatureKey;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(signatureKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verify = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(expiredTime.after(new Date()) && verify)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(),
                user.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {
        // create header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // create payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // username
                .issuer("java.com") // from who
                .issueTime(new Date()) // time create
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                        // expired after 1h
                ))
                .claim("scope", buildScope(user)) // content of token
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); // create payload
        //

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // signature
        try {
            jwsObject.sign(new MACSigner(signatureKey.getBytes()));
            return jwsObject.serialize(); // return type of string
        } catch (JOSEException joseException) {
            log.error("Can't not create token.");
            throw new RuntimeException(joseException);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
