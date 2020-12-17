package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.request.CamundaFormSubmitDTO;

public interface ProcessEngineService {

    String submitForm(CamundaFormSubmitDTO data);
}
