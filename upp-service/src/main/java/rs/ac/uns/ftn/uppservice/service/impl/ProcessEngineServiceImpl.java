package rs.ac.uns.ftn.uppservice.service.impl;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.service.ProcessEngineService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessEngineServiceImpl implements ProcessEngineService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * Sends the form to the Camunda and validates it.
     * @param data
     * @return process instance id
     */
    @Override
    public String submitForm(CamundaFormSubmitDTO data) {
        Map<String, Object> map = new HashMap<>();

        for(FormSubmissionDto temp : data.getFormData()){
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
}
