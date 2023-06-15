package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.*;
import com.munsun.system_projects.business.repository.*;
import com.munsun.system_projects.business.service.CommandService;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.CommandDtoIn;
import com.munsun.system_projects.dto.entity.in.CommandEmployeesDtoIn;
import com.munsun.system_projects.dto.entity.out.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CommandServiceImpl implements CommandService {
    private final Mapper<CommandEmployees, CommandEmployeesDtoOut> mapperCommandEmployees;
    private final Mapper<Command, CommandDtoOut> mapperCommand;
    private final Mapper<Employee, EmployeeDtoOut> mapperEmployee;

    private final CommandRepository commandRepository;
    private final CommandEmployeeRepository commandEmployeeRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleCommandRepository roleCommandRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public CommandServiceImpl(Mapper<CommandEmployees, CommandEmployeesDtoOut> mapperCommandEmployees,
                              Mapper<Command, CommandDtoOut> mapperCommand,
                              Mapper<Employee, EmployeeDtoOut> mapperEmployee,
                              CommandRepository commandRepository,
                              CommandEmployeeRepository commandEmployeeRepository,
                              EmployeeRepository employeeRepository,
                              RoleCommandRepository roleCommandRepository,
                              ProjectRepository projectRepository)
    {
        this.mapperCommandEmployees = mapperCommandEmployees;
        this.mapperCommand = mapperCommand;
        this.mapperEmployee = mapperEmployee;
        this.commandRepository = commandRepository;
        this.commandEmployeeRepository = commandEmployeeRepository;
        this.employeeRepository = employeeRepository;
        this.roleCommandRepository = roleCommandRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public CommandDtoOut createCommand(CommandDtoIn commandDtoIn) throws CommandDuplicateException, ProjectNotFoundException {
        log.debug("Сохранение команды: "+commandDtoIn);
            checkNullCommandDtoIn(commandDtoIn);
            checkProjectId(commandDtoIn.getIdProject());

            Project project = projectRepository.getReferenceById(commandDtoIn.getIdProject());
            checkDuplicateCommand(commandDtoIn.getIdProject());
            Command command = new Command();
                command.setCode(getCode());
                command.setProject(project);
            var result = commandRepository.save(command);
        log.debug("Сохранение команды успешно: " + result);
                return mapperCommand.reverseMap(result);
    }

    private void checkNullCommandDtoIn(CommandDtoIn commandDtoIn) {
        log.info("Проверка на NULL...");
            if(commandDtoIn == null) {
                log.error("Проверка на NULL провалено: "+commandDtoIn);
                throw new NullPointerException("commandDtoIn=null");
            }
        log.info("Проверка на NULL успешно");
    }

    private void checkProjectId(int id) throws ProjectNotFoundException {
        log.info("Проверка идентификатора проекта: "+id);
        if(!projectRepository.existsById(id)) {
            log.error("Проверка идентификатора проекта провалено: проекта с id="+id+"не существует");
            throw new ProjectNotFoundException();
        }
        log.info("Проверка идентификатора проекта успешно: "+id);
    }

    private void checkDuplicateCommand(int idProject) throws CommandDuplicateException {
        log.info("Проверка дубликатов команды...");
            if(commandRepository.existsCommandByProject_Id(idProject)) {
                log.error("Проверка дубликатов команды провалено: команда с idProject=" + idProject+" существует");
                throw new CommandDuplicateException();
            }
        log.info("Проверка дубликатов команды успешно");
    }

   private String getCode() {
        return String.valueOf(UUID.randomUUID());
   }

    @Override
    public CommandEmployeesDtoOut addEmployeeCommand(CommandEmployeesDtoIn commandEmployeesDtoIn) throws CommandNotFoundException, CommandEmployeeDuplicateException, EmployeeNotFoundException {
        log.debug("Добавление сотрудника в команду: "+commandEmployeesDtoIn);
            checkNullCommandEmployeesDtoIn(commandEmployeesDtoIn);
            int idCommand = commandEmployeesDtoIn.getIdCommand();
            checkExistsCommand(idCommand);
            Command command = commandRepository.getReferenceById(idCommand);

            int idEmployee = commandEmployeesDtoIn.getIdEmployee();
            checkExistsEmployee(idEmployee);
            Employee employee = employeeRepository.getReferenceById(idEmployee);

            checkNotExistsEmployeeToCommand(command, employee);
            RoleCommand roleCommand = roleCommandRepository.findRoleCommandByName(commandEmployeesDtoIn.getRoleCommand().name());

            CommandEmployees commandEmployees = new CommandEmployees();
                commandEmployees.setCommand(command);
                commandEmployees.setEmployee(employee);
                commandEmployees.setRoleCommand(roleCommand);
            var result = commandEmployeeRepository.save(commandEmployees);
        log.debug("Добавление сотрудника в команду успешно: "+result);
            return mapperCommandEmployees.reverseMap(result);
    }

    private void checkNullCommandEmployeesDtoIn(CommandEmployeesDtoIn commandEmployeesDtoIn) {
        log.info("Проверка на NULL...");
            if(commandEmployeesDtoIn == null) {
                log.error("Проверка на NULL провалено");
                throw new NullPointerException();
            }
        log.info("Проверка на NULL успешно");
    }

    private void checkExistsEmployee(int idEmployee) throws EmployeeNotFoundException {
        log.info("Проверка идентификатора сотрудника...");
        if(!employeeRepository.existsById(idEmployee)) {
            log.error("Проверка идентификатора сотрудника провалено: сотрудник id="+idEmployee);
            throw new EmployeeNotFoundException();
        }
        log.info("Проверка идентификатора сотрудника успешно");
    }

    private void checkExistsCommand(int id) throws CommandNotFoundException {
        log.info("Проверка идентификатора команды...");
            if(!commandRepository.existsById(id)) {
                throw new CommandNotFoundException();
            }
        log.info("Проверка идентификатора команды успешно");
    }

    private void checkNotExistsEmployeeToCommand(Command command, Employee employee) throws CommandEmployeeDuplicateException {
        log.info("Проверка сотрудника на отсутствие в команде...");
            if(commandEmployeeRepository.existsCommandEmployeesByCommandAndEmployee(command, employee)) {
                log.error("Проверка сотрудника на отсутствие в команде провалено: сотрудник id="+employee.getId()
                        +" уже состоит в команде id="+command.getId());
                throw new CommandEmployeeDuplicateException();
            }
        log.info("Проверка сотрудника на отсутствие в команде успешно");
    }

    @Override
    public EmployeeDtoOut removeEmployeeByProject(int idProject, int idEmployee) throws ProjectNotFoundException, EmployeeNotFoundException, CommandNotFoundException, CommandEmployeeNotFoundException {
        log.debug("Удаление сотрудника из команды по идентификаторам проекта и сотрудника: idProject="+idProject
                    +" idEmployee="+idEmployee);
            checkProjectId(idProject);
            checkEmployeeId(idEmployee);
            checkExistsCommandByProjectId(idProject);
            checkExistsCommandEmployee(idProject, idEmployee);

            Command command = commandRepository.findCommandByProject_Id(idProject);
            Employee employee = employeeRepository.getReferenceById(idEmployee);
            commandEmployeeRepository.deleteCommandEmployeesByCommandAndEmployee(command, employee);
        log.debug("Удаление сотрудника из команды по идентификаторам проекта и сотрудника успешно: idProject="+idProject
                +" idEmployee="+idEmployee);
            return mapperEmployee.reverseMap(employee);
    }

    private void checkEmployeeId(int idEmployee) throws EmployeeNotFoundException {
        log.info("Проверка идентификатора сотрудника: "+idEmployee);
            if(!employeeRepository.existsById(idEmployee)) {
                log.error("Проверка идентификатора сотрудника провалено: "+idEmployee);
                throw new EmployeeNotFoundException();
            }
        log.info("Проверка идентификатора сотрудника успешно: "+idEmployee);
    }

    private void checkExistsCommandByProjectId(int idProject) throws CommandNotFoundException {
        log.info("Проверка существования команды с проектом id="+idProject);
            if(!commandRepository.existsCommandByProject_Id(idProject)) {
                log.error("Проверка существования команды с проектом id="+idProject+" провалено");
                    throw new CommandNotFoundException();
            }
        log.info("Проверка существования команды с проектом id="+idProject+"успешно");
    }

    private void checkExistsCommandEmployee(int idProject, int idEmployee) throws CommandEmployeeNotFoundException {
        log.info("Проверка существования сотрудника id="+idEmployee+
                " в проекте id="+idProject);
            Command command = commandRepository.findCommandByProject_Id(idProject);
            Employee employee = employeeRepository.getReferenceById(idEmployee);
            boolean status = true;
            if(!commandEmployeeRepository.existsCommandEmployeesByCommandAndEmployee(command, employee)) {
                log.error("Проверка существования сотрудника id="+idEmployee+
                        " в проекте id="+idProject+" провалено");
                    throw new CommandEmployeeNotFoundException("Сотрудника id="+idEmployee+
                            " не состоит в команде id="+command.getId()+ " с проектом id="
                    +idProject);
            }
        log.info("Проверка существования сотрудника id="+idEmployee+
                " в проекте id="+idProject+" успешно: "+status);
    }

    @Override
    public List<CommandEmployeesDtoOut> getEmployeesByProject(int idProject) throws ProjectNotFoundException, CommandNotFoundException {
        log.debug("Получение всех членов команды по идентификатору проекта: id="+idProject);
            checkProjectId(idProject);
            checkExistsCommandByProjectId(idProject);

            Command command = commandRepository.findCommandByProject_Id(idProject);
            var commandEmployees = commandEmployeeRepository.findCommandEmployeesByCommand(command);
        log.debug("Получение всех членов команды по идентификатору проекта успешно: id="+idProject);
            return commandEmployees.stream()
                        .map(mapperCommandEmployees::reverseMap)
                        .collect(Collectors.toList());
    }

    @Override
    public List<CommandDtoOut> getAllCommands() {
        log.debug("Получение всех команд...");
            var commands = commandRepository.findAll();
        log.debug("Получение всех команд успешно");
            return commands.stream()
                    .map(mapperCommand::reverseMap)
                    .collect(Collectors.toList());
    }

    public CommandDtoOut getCommandById(int id) throws CommandNotFoundException {
        log.debug("Получение команды по идентификатору: id="+id);
            checkExistsCommand(id);

            var command = commandRepository.getReferenceById(id);
            var result = mapperCommand.reverseMap(command);
        log.debug("Получение команды по идентификатору успешно: "+result);
            return result;
    }
}
