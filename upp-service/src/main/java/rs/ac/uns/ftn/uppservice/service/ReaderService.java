package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.model.Reader;

import java.util.List;

public interface ReaderService {

    Reader add(List<FormSubmissionDto> formData);
}
