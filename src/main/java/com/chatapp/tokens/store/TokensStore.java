package com.chatapp.tokens.store;

import com.chatapp.tokens.domain.common.Provider;
import com.chatapp.tokens.domain.internal.Token;

public interface TokensStore {

    Token getToken(Provider provider, String externalId) throws CannotGetTokenException, UnknownTokenException;

    void putToken(Token token) throws CannotPutTokenException, TokenAlreadyExistsException;

    void updateToken(Token token) throws CannotUpdateTokenException, UnknownTokenException;

}
