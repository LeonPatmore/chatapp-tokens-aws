package com.chatapp.tokens.domain.external.requests;

import com.chatapp.tokens.domain.common.Provider;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RenewRequestSNS {

    @NotNull
    private Provider provider;

    @NotNull
    @Size(min=1)
    private String externalId;

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

}
