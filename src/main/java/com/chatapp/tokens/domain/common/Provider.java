package com.chatapp.tokens.domain.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Provider {

    @JsonProperty("whatsapp")
    WHATSAPP,

    @JsonProperty("messenger")
    MESSENGER

}
