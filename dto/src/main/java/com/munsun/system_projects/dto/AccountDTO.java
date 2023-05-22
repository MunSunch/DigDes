package com.munsun.system_projects.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class AccountDTO {
    private int id;
    private String login;
    private String password;
}
