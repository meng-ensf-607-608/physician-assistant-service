package com.harneet.ucal.physicianAssistantService.util;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class AuthToken {
    @JsonProperty
    private final String token;
    @JsonProperty
    private final Date expiry;

    public AuthToken(String token, Date expiry) {
        this.token = token;
        this.expiry = expiry;
    }
}

