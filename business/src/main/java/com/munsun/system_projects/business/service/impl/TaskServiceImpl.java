package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Command;
import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.business.model.Task;
import com.munsun.system_projects.business.repository.*;
import com.munsun.system_projects.business.service.TaskService;
import com.munsun.system_projects.business.service.impl.specification.TaskSpecification;
import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.TaskDtoIn;
import com.munsun.system_projects.dto.entity.out.TaskDtoOut;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final StatusTaskRepository statusTaskRepository;
    private final ProjectRepository projectRepository;
    private final CommandRepository commandRepository;
    private final CommandEmployeeRepository commandEmployeeRepository;

    private final Mapper<Task, TaskDtoOut> mapperTask;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           EmployeeRepository employeeRepository,
                           StatusTaskRepository statusTaskRepository,
                           ProjectRepository projectRepository,
                           CommandRepository commandRepository,
                           CommandEmployeeRepository commandEmployeeRepository,
                           Mapper<Task, TaskDtoOut> mapperTask)
    {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.statusTaskRepository = statusTaskRepository;
        this.projectRepository = projectRepository;
        this.commandRepository = commandRepository;
        this.commandEmployeeRepository = commandEmployeeRepository;
        this.mapperTask = mapperTask;
    }

    @Override
    public TaskDtoOut createTask(TaskDtoIn taskDtoIn) throws ProjectNotFoundException, EmployeeNotFoundException, CommandEmployeeNotFoundException, CommandNotFoundException {
        log.debug("Создание задачи: "+taskDtoIn);
            checkTaskFields(taskDtoIn);
            checkTaskExistsExecutor(taskDtoIn);
            checkTaskExistsProject(taskDtoIn);
            checkTaskExecutor(taskDtoIn);
            checkTaskDeadline(taskDtoIn);
            checkTaskAuthor(taskDtoIn);

            Task task = new Task();
                task.setName(taskDtoIn.getName());
                task.setDescription(taskDtoIn.getDescription());
                task.setExecutor(employeeRepository.getReferenceById(taskDtoIn.getIdEmployeeExecutor()));
                task.setCost(taskDtoIn.getCost());
                task.setEndDate(taskDtoIn.getEndDate());
                task.setCreateDate(new Timestamp(new java.util.Date().getTime()));
                task.setLastChangeDate(new Timestamp(new java.util.Date().getTime()));
                task.setAuthorTask(employeeRepository.getReferenceById(taskDtoIn.getIdEmployeeAuthor()));
                task.setStatusTask(statusTaskRepository.findStatusTaskByName(StatusTask.NEW.name()));
                task.setProject(projectRepository.getReferenceById(taskDtoIn.getIdProject()));
            var result = taskRepository.save(task);
        log.debug("Создание задачи успешно: "+result);
            return mapperTask.reverseMap(result);
    }

    private void checkTaskExistsProject(TaskDtoIn taskDtoIn) throws ProjectNotFoundException {
        log.info("Проверка идентификатора проекта: "+taskDtoIn.getIdProject());
        if(!employeeRepository.existsById(taskDtoIn.getIdEmployeeExecutor())) {
            log.error("Проверка идентификатора проекта провалено: "+taskDtoIn.getIdProject());
            throw new ProjectNotFoundException();
        }
        log.info("Проверка идентификатора проекта успешно: "+taskDtoIn.getIdProject());
    }

    private void checkTaskExistsExecutor(TaskDtoIn taskDtoIn) throws EmployeeNotFoundException {
        log.info("Проверка идентификатора исполнителя: "+taskDtoIn.getIdEmployeeExecutor());
            if(!employeeRepository.existsById(taskDtoIn.getIdEmployeeExecutor())) {
                log.error("Проверка идентификатора исполнителя провалено: "+taskDtoIn.getIdEmployeeExecutor());
                throw new EmployeeNotFoundException();
            }
        log.info("Проверка идентификатора исполнителя успешно: "+taskDtoIn.getIdEmployeeExecutor());
    }

    private void checkTaskFields(TaskDtoIn taskDtoIn) throws ProjectNotFoundException, EmployeeNotFoundException {
        log.info("Проверка обязательных полей задачи: "+taskDtoIn);
            if(ObjectUtils.isEmpty(taskDtoIn.getName())) {
                log.error("Проверка обязательных полей задачи провалено: name="+taskDtoIn.getName());
                throw new IllegalArgumentException("Имя не заполнено");
            }
            if(ObjectUtils.isEmpty(taskDtoIn.getCost())) {
                log.error("Проверка обязательных полей задачи провалено: cost="+taskDtoIn.getName());
                throw new IllegalArgumentException("Трудозатраты не заполнены");
            }
            if(ObjectUtils.isEmpty(taskDtoIn.getEndDate())) {
                log.error("Проверка обязательных полей задачи провалено: end_date="+taskDtoIn.getEndDate());
                throw new IllegalArgumentException("Крайний срок не заполнен");
            }
            checkIdProject(taskDtoIn.getIdProject());
            checkExistsExecutor(taskDtoIn.getIdEmployeeExecutor());
        log.info("Проверка обязательных полей задачи успешно: "+taskDtoIn);
    }

    private void checkIdProject(int idProject) throws ProjectNotFoundException {
        log.info("Проверка идентификатора проекта: "+idProject);
        if(!projectRepository.existsById(idProject)) {
            log.error("Проверка идентификатора проекта провалено: "+idProject);
            throw new ProjectNotFoundException();
        }
        log.info("Проверка идентификатора проекта успешно: "+idProject);
    }

    private void checkExistsExecutor(int idEmployeeExecutor) throws EmployeeNotFoundException {
        log.info("Проверка идентификатора исполнителя: "+idEmployeeExecutor);
            if(idEmployeeExecutor != 0) {
                if(!employeeRepository.existsById(idEmployeeExecutor)) {
                    log.error("Проверка идентификатора исполнителя провалено: "+idEmployeeExecutor);
                    throw new EmployeeNotFoundException();
                }
            }
        log.info("Проверка идентификатора исполнителя успешно: "+idEmployeeExecutor);
    }

    private void checkTaskExecutor(TaskDtoIn taskDtoIn) throws ProjectNotFoundException, CommandNotFoundException, CommandEmployeeNotFoundException {
        log.info("Проверка исполнителя задачи: "+taskDtoIn.getIdEmployeeExecutor());
            if(taskDtoIn.getIdEmployeeExecutor() != 0) {
                int idProject = taskDtoIn.getIdProject();
                checkExistsCommandByProjectId(idProject);
                Employee employee = employeeRepository.getReferenceById(taskDtoIn.getIdEmployeeExecutor());
                Command command = commandRepository.findCommandByProject_Id(idProject);
                if(!commandEmployeeRepository.existsCommandEmployeesByCommandAndEmployee(command, employee)) {
                    log.error("Проверка исполнителя задачи провалено: "+taskDtoIn.getIdEmployeeExecutor());
                    throw new CommandEmployeeNotFoundException("сотрудник id="+taskDtoIn.getIdEmployeeExecutor()
                            +" не состоит в проекте id="+taskDtoIn.getIdProject());
                }
            }
        log.info("Проверка исполнителя задачи успешно: "+taskDtoIn.getIdEmployeeExecutor());
    }

    private void checkExistsCommandByProjectId(int id) throws CommandNotFoundException {
        log.info("Проверка существования команды по идентификатору проекта: "+id);
            if(!commandRepository.existsCommandByProject_Id(id)) {
                log.error("Проверка существования команды по идентификатору проекта провалено: "+id);
                throw new CommandNotFoundException();
            }
        log.info("Проверка существования команды по идентификатору проекта успешно: "+id);
    }

    private void checkTaskDeadline(TaskDtoIn taskDtoIn) {
        log.info("Проверка крайнего срока задачи: "+taskDtoIn.getEndDate());
            var endDate = taskDtoIn.getEndDate();
            var targetEndDate = new Timestamp(new java.util.Date().getTime()+taskDtoIn.getCost()*3_600_000L);
            if(endDate.before(targetEndDate)) {
                log.error("Проверка крайнего срока задачи провалено: "
                        +"endDate="+endDate+", targetEndDate="+targetEndDate);
                throw new IllegalArgumentException("Дедлайн/трудозатраты указаны неверно");
            }
        log.info("Проверка крайнего срока задачи успешно: "+taskDtoIn.getEndDate());
    }

    private void checkTaskAuthor(TaskDtoIn taskDtoIn) throws CommandEmployeeNotFoundException {
        log.info("Проверка автора: "+ taskDtoIn.getIdEmployeeAuthor());
            Command command = commandRepository.findCommandByProject_Id(taskDtoIn.getIdProject());
            Employee employee = employeeRepository.getReferenceById(taskDtoIn.getIdEmployeeAuthor());
            if(!commandEmployeeRepository.existsCommandEmployeesByCommandAndEmployee(command, employee)) {
                log.error("Проверка автора провалено: "+taskDtoIn.getIdEmployeeAuthor());
                throw new CommandEmployeeNotFoundException("сотрудник id="+taskDtoIn.getIdEmployeeExecutor()
                        +" не состоит в проекте id="+taskDtoIn.getIdProject());
            }
        log.info("Проверка автора успешно: "+ taskDtoIn.getIdEmployeeAuthor());
    }

    @Override
    public TaskDtoOut setTask(int id, TaskDtoIn taskDtoIn) throws ProjectNotFoundException, EmployeeNotFoundException, CommandEmployeeNotFoundException, CommandNotFoundException, TaskNotFoundException {
        log.debug("Редактирование задачи: "+taskDtoIn);
            checkUpdateTaskId(id);
            checkTaskFields(taskDtoIn);
            checkTaskExistsExecutor(taskDtoIn);
            checkTaskExistsProject(taskDtoIn);
            checkTaskExecutor(taskDtoIn);
            checkTaskDeadline(taskDtoIn);
            checkTaskAuthor(taskDtoIn);

            Task task = taskRepository.getReferenceById(id);
                task.setName(taskDtoIn.getName());
                task.setDescription(taskDtoIn.getDescription());
                task.setExecutor(employeeRepository.getReferenceById(taskDtoIn.getIdEmployeeExecutor()));
                task.setCost(taskDtoIn.getCost());
                task.setEndDate(taskDtoIn.getEndDate());
                task.setLastChangeDate(new Timestamp(new java.util.Date().getTime()));
                task.setAuthorTask(employeeRepository.getReferenceById(taskDtoIn.getIdEmployeeAuthor()));
                task.setStatusTask(statusTaskRepository.findStatusTaskByName(StatusTask.NEW.name()));
                task.setProject(projectRepository.getReferenceById(taskDtoIn.getIdProject()));
            var result = taskRepository.save(task);
        log.debug("Редактирование задачи успешно: "+result);
            return mapperTask.reverseMap(result);
    }

    private void checkUpdateTaskId(int id) throws TaskNotFoundException {
        log.info("Проверка идентификатора задачи: "+id);
            if(!taskRepository.existsById(id)) {
                log.error("Проверка идентификатора задачи провалено: "+id);
                throw new TaskNotFoundException();
            }
        log.info("Проверка идентификатора задачи успешно: "+id);
    }

    @Override
    public List<TaskDtoOut> getTasks(TaskDtoIn taskDtoIn, StatusTask ...statuses) {
        log.debug("Получение задач по имени и " +
                "с применением фильтров по статусам задачи, по исполнителю," +
                " по автору задачи, по периоду крайнего срока задачи," +
                " по периоду создания задачи"+taskDtoIn);
            List<Task> tasks = taskRepository.findAll(TaskSpecification.filter(taskDtoIn, statuses));
        log.debug("Получение задач по имени и " +
                "с применением фильтров по статусам задачи, по исполнителю," +
                " по автору задачи, по периоду крайнего срока задачи," +
                " по периоду создания задачи успешно: count="+tasks.size());
            return tasks.stream()
                    .map(mapperTask::reverseMap)
                    .collect(Collectors.toList());
    }

    @Override
    public TaskDtoOut updateStatusTask(int id, StatusTask status) throws StatusTaskUpdateException, TaskNotFoundException {
        log.debug("Изменение статуса задачи: id="+id+" ,status="+status);
            checkUpdateTaskId(id);
            Task task = taskRepository.getReferenceById(id);
            checkTaskUpdateStatus(task.getStatusTask().getName(), status.name());
            task.setStatusTask(statusTaskRepository.findStatusTaskByName(status.name()));
            var result = taskRepository.save(task);
        log.debug("Изменение статуса задачи: id="+id+" ,status="+status);
            return mapperTask.reverseMap(result);
    }

    private void checkTaskUpdateStatus(String oldNameStatusTask, String newNameStatusTask) throws StatusTaskUpdateException {
        log.info("Проверка нового статуса...");
            StatusTask oldStatus = StatusTask.valueOf(oldNameStatusTask);
            StatusTask newStatus = StatusTask.valueOf(newNameStatusTask);
            if(oldStatus.ordinal() > newStatus.ordinal()) {
                log.error("Проверка нового статуса провалено: "+newNameStatusTask);
                throw new StatusTaskUpdateException("Некорректный статус! Статус должен быть "
                        + Arrays.stream(StatusTask.values())
                            .filter(x->x.ordinal()<= newStatus.ordinal())
                            .toList());
            }
        log.info("Проверка нового статуса успешно: "+newNameStatusTask);
    }
}
