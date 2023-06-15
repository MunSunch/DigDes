package com.munsun.system_projects.dto.entity.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Сотрудник")
public class EmployeeDtoIn {
    @Schema(description = "Имя")
    @NotNull
    @JsonProperty("name")
    private String name;

    @Schema(description = "Фамилия")
    @NotNull
    @JsonProperty("lastname")
    private String lastname;

    @Schema(description = "Отчество")
    @JsonProperty("pytronymic")
    private String pytronymic;

    @Schema(description = "Должность")
    @NotNull
    @JsonProperty("post")
    private PostEmployee postEmployee;

    @Schema(description = "Учетная запись")
    @NotNull
    @JsonProperty("account")
    private AccountDtoIn account;

    @Schema(description = "Электронная почта")
    @JsonProperty("email")
    private String email;

    @JsonSetter
    public void addAccount(String login, String password) {
        account.setLogin(login);
        account.setPassword(password);
    }
}
