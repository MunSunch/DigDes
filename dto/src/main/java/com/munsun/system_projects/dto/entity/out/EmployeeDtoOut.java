package com.munsun.system_projects.dto.entity.out;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Сотрудник")
public class EmployeeDtoOut {
    @Schema(description = "Идентификатор")
    @Size(min = 1)
    private int id;

    @Schema(description = "Имя")
    @NotNull
    private String name;

    @Schema(description = "Фамилия")
    @NotNull
    private String lastname;

    @Schema(description = "Отчество")
    private String pytronymic;

    @Schema(description = "Должность")
    @NotNull
    private PostEmployee postEmployee;

    @Schema(description = "Учетная запись")
    @NotNull
    private AccountDtoOut account;

    @Schema(description = "Электронная почта")
    private String email;

    @Schema(description = "Статус")
    @NotNull
    private StatusEmployee statusEmployee;

    @JsonAnySetter
    public void addAccountDTO(String login, String password) {
        account.setLogin(login);
        account.setPassword(password);
    }
}
