package rs.ac.uns.ftn.uppservice.service.delegates.file;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Writer;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;
import java.io.IOException;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WORK_COUNT;
import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WRITER;

@Component
@RequiredArgsConstructor
public class ExtraPaperDelegate implements JavaDelegate {

    private final FileService fileService;

    @Override
    public void execute(DelegateExecution execution) throws IOException {
        File pdfFile = (File) execution.getVariable(Constants.SUBMIT_FILE_DATA);
        Writer writer = (Writer) execution.getVariable(WRITER);

        fileService.saveFile(writer.getUsername(), execution.getProcessInstanceId(), pdfFile, true);
        execution.setVariable(WORK_COUNT, 2);
    }
}
