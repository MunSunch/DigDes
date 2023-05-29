package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description="Учетная запись сотрудника")
public class AccountDTO {
    @Schema(description="Идентификатор")
    @Size(min = 1)
    @JsonProperty("id")
    private int id;

    @NotNull
    @Schema(description = "Логин")
    @JsonProperty("login")
    private String login;

    @NotNull
    @Schema(description="Пароль")
    @JsonProperty("password")
    private String password;
}