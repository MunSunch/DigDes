package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description="Учетная запись сотрудника")
public class AccountDTO {
    @Schema(description="Идентификатор")
    @Min(1)
    @Max(Integer.MAX_VALUE)
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