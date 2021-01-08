package rs.ac.uns.ftn.uppservice.service.listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.camunda.formfields.MultiSelectFormFieldType;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.model.Reader;
import rs.ac.uns.ftn.uppservice.repository.ReaderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddBetaReadersToFormField implements TaskListener {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData formData = delegateTask
                .getExecution()
                .getProcessEngineServices()
                .getFormService()
                .getTaskFormData(delegateTask.getId());

        FormField field = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("FormField_betaReaders"))
                .findFirst()
                .get();

        MultiSelectFormFieldType fieldType = (MultiSelectFormFieldType) field.getType();
        List<Reader> betaReaders = readerRepository.findByIsBetaReader(true);
        Book book = (Book) delegateTask.getVariable("book");

        List<Reader> filteredReaders = betaReaders.stream()
                .filter(r -> r.getBetaGenres().contains(book.getGenre()))
                .collect(Collectors.toList());

        betaReaders.forEach(reader -> fieldType.getValues().put(reader.getId().toString(), reader.getUsername()));
        // TODO: ova je ispravan nacin biranja betareadera, ovo gore je samo za testiranje
        //        filteredReaders.forEach(reader -> fieldType.getValues().put(reader.getId().toString(), reader.getUsername()));
    }
}
