package rs.ac.uns.ftn.uppservice.service.delegates.publishing;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Jurisdiction;
import rs.ac.uns.ftn.uppservice.repository.BookRepository;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;

@Component
@RequiredArgsConstructor
public class SaveEditedPdfHandwritingDelegate implements JavaDelegate {

    private final FileService fileService;
    private final BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        File pdfFile = (File) execution.getVariable(Constants.SUBMIT_FILE_DATA);
        Book book = (Book) execution.getVariable(Constants.BOOK);


        File previousHandwriting = new File(book.getHandwritePath());
        previousHandwriting.delete();

        book.setJurisdiction(Jurisdiction.EDITORS);
        bookRepository.save(book);
        execution.setVariable(Constants.BOOK, book);

        fileService.saveFile(book.getWriter().getUsername(), execution.getProcessInstanceId(), pdfFile, false);
    }
}

