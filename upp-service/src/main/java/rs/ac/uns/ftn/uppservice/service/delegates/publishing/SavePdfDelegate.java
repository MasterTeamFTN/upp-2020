package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;

@Component
@RequiredArgsConstructor
public class SavePdfDelegate implements JavaDelegate {

    private final FileService fileService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        File pdfFile = (File) execution.getVariable(Constants.SUBMIT_FILE_DATA);
        Book book = (Book) execution.getVariable(Constants.BOOK);
        String tempPath = (String) execution.getVariable(Constants.TEMP_FILE_PATH);

        fileService.saveFile(book.getWriter().getUsername(), execution.getProcessInstanceId(), pdfFile, false);

        File tempFile = new File(tempPath);
        tempFile.delete();
    }
}
