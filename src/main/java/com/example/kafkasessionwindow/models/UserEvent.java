package com.example.kafkasessionwindow.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserEvent {
    @JsonProperty("userType")
    private String userType;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("idType")
    private String idType;
    @JsonProperty("id")
    private String id;
    @JsonProperty("createdTime")
    private Long createdTime;
}
