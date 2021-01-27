package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.FormService;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final FormService formService;

    @Override
    public boolean submitPayment(CamundaFormSubmitDTO data, String taskId) {
        Map<String, Object> map = new HashMap<>();

        for (FormSubmissionDto temp : data.getFormData()) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        formService.submitTaskForm(taskId, map);
        return true;
    }

}
