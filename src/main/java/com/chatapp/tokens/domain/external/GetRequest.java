package com.chatapp.tokens.domain.external;

import com.chatapp.tokens.domain.common.Provider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class GetRequest {

    private PathParameters pathParameters;

    public PathParameters getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(PathParameters pathParameters) {
        this.pathParameters = pathParameters;
    }

    public class PathParameters {

        private Provider provider;

        private String id;

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

}
