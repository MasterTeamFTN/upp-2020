package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.io.File;
import java.util.*;

import static java.util.Objects.isNull;
import static rs.ac.uns.ftn.uppservice.common.constants.Constants.*;

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
    public String submitForm(CamundaFormSubmitDTO data, boolean betaGenres) {
        Map<String, Object> map = new HashMap<>();

        for (FormSubmissionDto temp : data.getFormData()) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        try {
            // @Hack: Boris - ovde sam stavio da se setuje svaka forma koja stigne kao procesna varijabla
            // jer je bilo problema da se ovde pozove .submitTaskForm koji odma prebaci na sledeci task
            // u procesu. Ako je tom sledecem tasku potrebna ova forma, Spring nece stici da je setuje
            // tako da ce uvek dolaziti do exceptiona
            // Ono sta se meni desavalo je da kad uradim submit, pozove se ova metoda, zatim se odma
            // aktivira Delegat sledeceg taska, pa se tek onda setuje forma u kontroleru za submitovanje
            runtimeService.setVariable(processInstanceId, "formData", data.getFormData());

            if(betaGenres) {
                runtimeService.setVariable(processInstanceId, CHOOSE_GENRES_FORM_DATA, data.getFormData());
            }else {
                runtimeService.setVariable(processInstanceId, REGISTRATION_FORM_DATA, data.getFormData());
            }

            formService.submitTaskForm(data.getTaskId(), map);
        } catch (FormFieldValidatorException e) {
            throw new ApiRequestException("Failed validation");
        }

        return processInstanceId;
    }

    @Override
    public String submitForm(CamundaFormSubmitDTO data) {
        Map<String, Object> map = new HashMap<>();

        for (FormSubmissionDto temp : data.getFormData()) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        Task task = taskService.createTaskQuery().taskId(data.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        try {
            // @Hack: Boris - ovde sam stavio da se setuje svaka forma koja stigne kao procesna varijabla
            // jer je bilo problema da se ovde pozove .submitTaskForm koji odma prebaci na sledeci task
            // u procesu. Ako je tom sledecem tasku potrebna ova forma, Spring nece stici da je setuje
            // tako da ce uvek dolaziti do exceptiona
            // Ono sta se meni desavalo je da kad uradim submit, pozove se ova metoda, zatim se odma
            // aktivira Delegat sledeceg taska, pa se tek onda setuje forma u kontroleru za submitovanje
            runtimeService.setVariable(processInstanceId, "formData", data.getFormData());
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

        try {
            runtimeService.setVariable(processInstanceId, SUBMIT_FILE_DATA, convertedFile);
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
            formService.submitTaskForm(task.getId(), map);
        } catch (FormFieldValidatorException e) {
            throw new ApiRequestException("Failed validation");
        }


        return processInstanceId;
    }

    /**
     * Method used to add new decision to the list of decisions located in the runtime service. If the decision if
     * the first submitted, new list is being created.
     *
     * @param task
     * @param decisionVariable
     */
    private void saveToRuntime(Task task, String decisionVariable) {
        List<String> boardMemberDecisions = (List<String>) runtimeService.getVariable(task.getExecutionId(), BOARD_MEMBER_DECISIONS);

        if(!isNull(boardMemberDecisions)) {
            boardMemberDecisions.add(decisionVariable);
            runtimeService.setVariable(task.getExecutionId(), BOARD_MEMBER_DECISIONS, boardMemberDecisions);
        }else {
            List<String> decisions = new ArrayList<>();
            decisions.add(decisionVariable);
            runtimeService.setVariable(task.getExecutionId(), BOARD_MEMBER_DECISIONS, decisions);
        }
    }

}
