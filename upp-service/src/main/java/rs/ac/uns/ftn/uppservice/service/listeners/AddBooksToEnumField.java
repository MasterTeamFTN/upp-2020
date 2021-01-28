package rs.ac.uns.ftn.uppservice.service.listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Writer;
import rs.ac.uns.ftn.uppservice.repository.BookRepository;

import java.util.List;

@Service
public class AddBooksToEnumField implements TaskListener {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData formData = delegateTask
                .getExecution()
                .getProcessEngineServices()
                .getFormService()
                .getTaskFormData(delegateTask.getId());

        FormField originalBookField = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("FormField_originalBook"))
                .findFirst()
                .get();
        FormField plagiarisedBookField = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("FormField_plagiarismBook"))
                .findFirst()
                .get();

        EnumFormType originalBookFieldType = (EnumFormType) originalBookField.getType();
        EnumFormType plagiarisedBookFieldType = (EnumFormType) plagiarisedBookField.getType();
        
        List<Book> books = bookRepository.findAll();

        books.forEach(book ->
                originalBookFieldType.getValues().put(book.getId().toString(), bookSignature(book)));

        books.forEach(book ->
                plagiarisedBookFieldType.getValues().put(book.getId().toString(), bookSignature(book)));
    }

    private String bookSignature(Book book) {
        return book.getTitle() + ", " + book.getWriter().getFirstName() + " " + book.getWriter().getLastName();
    }
}
