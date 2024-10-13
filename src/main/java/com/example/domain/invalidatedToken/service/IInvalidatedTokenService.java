package com.example.domain.invalidatedToken.service;

import java.time.Instant;

/** Service interface for invalidated token service. */
public interface IInvalidatedTokenService {
    /**
     * Creates an invalidated token.
     *
     * @param token the token to create
     * @param expiresAt the time at which the token expires
     */
    void create(String token, Instant expiresAt);
}
