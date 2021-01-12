package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ProcessEngineServiceImpl implements ProcessEngineService {

    private final TaskService taskService;
    private final FormService formService;
    private final RuntimeService runtimeService;

    /**
     * Sends the form to the Camunda and validates it.
     *
     * @param data
     * @return process instance id
     */
    @Override
    public String submitForm(CamundaFormSubmitDTO data) {
        Map<String, Object> map = new HashMap<>();

        for (FormSubmissionDto temp : data.getFormData()) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        try {
            runtimeService.setVariable(processInstanceId, "registrationFormData", data.getFormData());
            formService.submitTaskForm(data.getTaskId(), map);
        } catch (FormFieldValidatorException e) {
            throw new ApiRequestException("Failed validation");
        }

        return processInstanceId;
    }

    @Override
    public String submitFile(String taskId, MultipartFile file, File convertedFile) {
        Map<String, Object> map = new HashMap<>();

        map.put("pdfFile", convertedFile);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
//        var workCount = runtimeService.getVariables(processInstanceId).get("workCount");

        try {
            runtimeService.setVariable(processInstanceId, "submitFileData", convertedFile);
            formService.submitTaskForm(taskId, map);
        } catch (FormFieldValidatorException e) {
            throw new ApiRequestException("Failed validation");
        }

        return processInstanceId;
    }

    @Override
    public String submitDecision(CamundaFormSubmitDTO data) {
        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String decisionVariable = (String) data.getFormData().get(0).getFieldValue();

        Map<String, Object> map = new HashMap<>();
        map.put("decision", decisionVariable.substring(0, 1).toLowerCase() + decisionVariable.substring(1));

        saveToRuntime(task, decisionVariable);

        try {
//            runtimeService.setVariable(processInstanceId, "decision", decisionVariable);
            formService.submitTaskForm(task.getId(), map);
        } catch (FormFieldValidatorException e) {
            throw new ApiRequestException("Failed validation");
        }


        return processInstanceId;
    }

    private void saveToRuntime(Task task, String decisionVariable) {
        List<String> boardMemberDecisions = (List<String>) runtimeService.getVariable(task.getExecutionId(), "boardMemberDecision");

        if(!isNull(boardMemberDecisions)) {
            boardMemberDecisions.add(decisionVariable);
            runtimeService.setVariable(task.getExecutionId(), "boardMemberDecision", boardMemberDecisions);
        }else {
            List<String> decisions = new ArrayList<>();
            decisions.add(decisionVariable);
            runtimeService.setVariable(task.getExecutionId(), "boardMemberDecision", decisions);
        }
    }



}
