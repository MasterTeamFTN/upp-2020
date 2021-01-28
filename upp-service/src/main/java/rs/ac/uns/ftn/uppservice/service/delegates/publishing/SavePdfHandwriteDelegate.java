package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.repository.BookRepository;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;

@Component
@RequiredArgsConstructor
public class SavePdfHandwriteDelegate implements JavaDelegate {

    private final FileService fileService;
    private final BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        File pdfFile = (File) execution.getVariable(Constants.SUBMIT_FILE_DATA);
        Book book = (Book) execution.getVariable(Constants.BOOK);
        String tempPath = (String) execution.getVariable(Constants.TEMP_FILE_PATH);

        String handwritePath = fileService.saveFile(book.getWriter().getUsername(), execution.getProcessInstanceId(), pdfFile, false);


        book.setHandwritePath(handwritePath);
        book = bookRepository.save(book);
        execution.setVariable(Constants.BOOK, book);

        File tempFile = new File(tempPath);
        tempFile.delete();
    }
}

