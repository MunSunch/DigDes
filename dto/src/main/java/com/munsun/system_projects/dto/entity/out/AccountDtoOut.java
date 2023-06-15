package com.munsun.system_projects.dto.entity.out;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Учетная запись сотрудника")
public class AccountDtoOut {
    @Schema(description = "идентификатор аккаунта")
    @Size(min=1)
    private int id;

    @Schema(description = "логин")
    @NotNull
    private String login;

    @Schema(description = "пароль")
    private String password;
}