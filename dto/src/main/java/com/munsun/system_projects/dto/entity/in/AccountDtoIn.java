package com.munsun.system_projects.dto.entity.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description="Учетная запись сотрудника")
public class AccountDtoIn {
    @NotNull
    @Schema(description = "Логин")
    @NotNull
    @JsonProperty("login")
    private String login;

    @Schema(description = "Пароль")
    @JsonProperty("password")
    private String password;
}
