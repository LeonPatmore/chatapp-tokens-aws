package com.chatapp.tokens.manager;

import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.domain.internal.TokenCredentials;
import com.chatapp.tokens.utils.ConstraintViolationException;
import com.chatapp.tokens.utils.GenericViolation;

import java.util.List;

public abstract class TokenManager {

    Token getToken(String externalId, TokenCredentials tokenCredentials) throws ConstraintViolationException {
        List<GenericViolation> genericViolations = validateToken(tokenCredentials);
        if (!genericViolations.isEmpty()) {
            throw new ConstraintViolationException(genericViolations);
        }
        return getTokenImplementation(externalId, tokenCredentials);
    }

    abstract Token getTokenImplementation(String externalId, TokenCredentials tokenCredentials);

    abstract List<GenericViolation> validateToken(TokenCredentials tokenCredentials);


}
