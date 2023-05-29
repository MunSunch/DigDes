package com.munsun.system_projects.dto.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Сотрудник")
public class EmployeeDTO {
    @Schema(description = "Идентификатор")
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty("id")
    private int id;

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
    private AccountDTO account;

    @Schema(description = "Электронная почта")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Статус")
    @NotNull
    @JsonProperty("status")
    private StatusEmployee statusEmployee;

    @JsonAnySetter
    public void addAccountDTO(String login, String password) {
        account.setLogin(login);
        account.setPassword(password);
    }
}
