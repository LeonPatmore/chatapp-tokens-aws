package com.chatapp.tokens.store;

import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;

public interface TokensStore {

    void ensureTokenNotExisting(Provider provider, String externalId) throws TokenAlreadyExistsException;

    Token getToken(Provider provider, String externalId) throws UnknownTokenException;

    void putToken(Token token) throws TokenAlreadyExistsException;

    void updateToken(Token token) throws UnknownTokenException;

}
