package com.munsun.system_projects.business.service.impl;

import com.munsun.system_projects.business.mapping.Mapper;
import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.repository.ProjectRepository;
import com.munsun.system_projects.business.repository.StatusProjectRepository;
import com.munsun.system_projects.business.service.ProjectService;
import com.munsun.system_projects.business.service.impl.specification.ProjectSpecification;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.commons.exceptions.*;
import com.munsun.system_projects.dto.entity.in.ProjectDtoIn;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final StatusProjectRepository statusProjectRepository;

    private final Mapper<Project, ProjectDtoOut> projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              StatusProjectRepository statusProjectRepository,
                              Mapper<Project, ProjectDtoOut> projectMapper)
    {
        this.projectRepository = projectRepository;
        this.statusProjectRepository = statusProjectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDtoOut createProject(ProjectDtoIn projectDtoIn) throws ProjectDuplicateException, ProjectEmptyFieldsException {
        log.debug("Создание нового проекта: "+projectDtoIn);
            checkEmptyName(projectDtoIn.getName());
            checkDuplicateProject(projectDtoIn);

            Project project = new Project();
                project.setCode(getProjectCode());
                project.setName(projectDtoIn.getName());
                project.setDescription(projectDtoIn.getDescription());
                project.setStatus(statusProjectRepository.findByName(StatusProject.DRAFT.name()));
            Project result = projectRepository.save(project);
        log.debug("Создание нового проекта успешно: "+result);
            return projectMapper.reverseMap(result);
    }

    private void checkEmptyName(String name) throws ProjectEmptyFieldsException {
        log.info("Проверка имени проекта на заполненность: "+name);
            if(name==null || "".equals(name)) {
                log.error("Проверка полей проекта на заполненность провалено: name=empty/null");
                throw new ProjectEmptyFieldsException();
            }
        log.info("Проверка полей проекта на заполненность...");
    }

    private void checkDuplicateProject(ProjectDtoIn projectDtoIn) throws ProjectDuplicateException {
        log.info("Поиск дубликатов проектов...");
        if(projectRepository.exists(ProjectSpecification.equals(projectDtoIn))) {
            log.error("Поиск дубликатов проектов провалено: проект с таким именем или описанием уже существует;{"
                    +"name="+projectDtoIn.getName()
                    +"description="+projectDtoIn.getDescription()+"}");
            throw new ProjectDuplicateException();
        }
        log.info("Поиск дубликатов проектов...");
    }

    private String getProjectCode() {
        return String.valueOf(UUID.randomUUID());
    }

    @Override
    public ProjectDtoOut setProject(int id, ProjectDtoIn projectDtoIn) throws ProjectDuplicateException,
                                                                              ProjectNotFoundException,
                                                                              ProjectEmptyFieldsException
    {
        log.debug("Изменение проекта...");
            checkExistsProject(id);
            checkEmptyName(projectDtoIn.getName());

            Project oldProject = projectRepository.getReferenceById(id);
                oldProject.setName(projectDtoIn.getName());
                oldProject.setDescription(projectDtoIn.getDescription());
            Project result = projectRepository.save(oldProject);
        log.debug("Изменение проекта прошло успешно");
            return projectMapper.reverseMap(result);
    }

    private void checkExistsProject(int id) throws ProjectNotFoundException {
        log.info("Проверка идентификатора: "+id);
        if(!projectRepository.existsById(id)) {
            log.error("Проекта с таким идентификатором не существует: id=" + id);
            throw new ProjectNotFoundException();
        }
        log.info("Проверка идентификатора успешно: "+id);
    }

    @Override
    public List<ProjectDtoOut> getProjectsByStringAndStatuses(String str, StatusProject... statusProjects) {
        log.debug("Получение проектов по наименованию, коду и статусам");
            checkEmptyString(str);
            var projects = projectRepository.findAll(ProjectSpecification.filter(str, statusProjects));
        log.debug("Получение проектов по наименованию, коду и статусам прошло успешно");
            return projects.stream()
                    .map(projectMapper::reverseMap)
                    .collect(Collectors.toList());
    }

    private void checkEmptyString(String str) {
        log.info("Проверка входной строки: "+str);
        if("".equals(str) || str==null) {
            log.error("Проверка входной строки провалено: str="+str);
            throw new IllegalArgumentException("Строка не заполнена: str="+str);
        }
        log.info("Проверка входной строки успешно: "+str);
    }

    @Override
    public ProjectDtoOut getProjectById(int id) throws ProjectNotFoundException {
        log.debug("Получение проекта по идентификатору...");
            checkExistsProject(id);
            Project result = projectRepository.getReferenceById(id);
        log.debug("Получение проекта по идентификатору прошло успешно");
            return projectMapper.reverseMap(result);
    }

    @Override
    public ProjectDtoOut updateStatusProject(int id, StatusProject newStatus) throws ProjectNotFoundException, ProjectIncorrectStatusUpdateException, ProjectEmptyFieldsException {
        log.debug("Обновление статуса проекта...");
            checkExistsProject(id);
            checkProjectNullStatus(newStatus);

            Project project = projectRepository.getReferenceById(id);
            StatusProject currentStatus = StatusProject.valueOf(project.getStatus().getName());
                checkStatusProject(currentStatus, newStatus);
            project.setStatus(statusProjectRepository.findByName(newStatus.name()));
            Project result = projectRepository.save(project);
        log.debug("Обновление статуса проекта завершено");
            return projectMapper.reverseMap(result);
    }

    private void checkStatusProject(StatusProject oldStatus, StatusProject newStatus) throws ProjectIncorrectStatusUpdateException {
        log.info("Проверка статуса: "+oldStatus);
            if(oldStatus.ordinal() > newStatus.ordinal()) {
                log.error("Проверка статуса провалено! new=" + newStatus.name()+" old="+oldStatus.name());
                throw new ProjectIncorrectStatusUpdateException("Некорректный статус! Статус должен быть "
                        + Arrays.stream(StatusProject.values())
                                .filter(x->x.ordinal()<= newStatus.ordinal())
                                .toList());
            }
        log.info("Проверка статуса успешно: "+oldStatus);
    }

    private void checkProjectNullStatus(StatusProject newStatus) throws ProjectEmptyFieldsException {
        log.info("Проверка статуса на NULL...");
            if(newStatus == null) {
                log.error("Проверка статуса на NULL провалено: status="+newStatus);
                    throw new ProjectEmptyFieldsException();
            }
        log.info("Проверка статуса на NULL успешно: "+newStatus);
    }
}
