package com.chatapp.tokens.manager;

import com.chatapp.tokens.domain.internal.Token;
import com.chatapp.tokens.domain.internal.TokenCredentials;
import com.chatapp.tokens.utils.GenericViolation;

import java.util.*;

public class WhatsappTokenManager extends TokenManager {

    @Override
    Token getTokenImplementation(String externalId, TokenCredentials tokenCredentials) {
        return null;
    }

    @Override
    List<GenericViolation> validateToken(TokenCredentials tokenCredentials) {
        List<GenericViolation> genericViolations = new ArrayList<>();
        if (Objects.isNull(tokenCredentials.getUsername())) {
            genericViolations.add(new GenericViolation("credentials.username", "can not be null", "null"));
        }
        if (Objects.isNull(tokenCredentials.getPassword())) {
            genericViolations.add(new GenericViolation("credentials.password", "can not be null", "null"));
        }
        return genericViolations;
    }

    private static String getEndpoint() {
        return String.format("asd");
    }

}
