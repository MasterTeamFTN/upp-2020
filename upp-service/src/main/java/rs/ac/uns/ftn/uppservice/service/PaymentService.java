package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;

public interface PaymentService {

    /**
     * Mock method for payment functionality
     *
     * @param data
     * @return
     */
    boolean submitPayment(CamundaFormSubmitDTO data, String taskId);
}
