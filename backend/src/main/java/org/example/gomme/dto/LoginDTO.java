package org.example.gomme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {
    @NotEmpty
    @NotBlank
    @JsonProperty("username")
    private String username;

    @NotEmpty
    @NotBlank
    @JsonProperty("password")
    private String password;
}
