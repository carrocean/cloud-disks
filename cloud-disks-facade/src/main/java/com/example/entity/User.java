package com.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @JsonProperty(value="UserId")
    private Integer UserId;
    @JsonProperty(value="UserName")
    private String UserName;
    @JsonProperty(value="Pwd")
    private String Pwd;
    private String token;
}
