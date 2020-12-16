package rs.ac.uns.ftn.uppservice.service.listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.camunda.formfields.MultiSelectFormFieldType;
import rs.ac.uns.ftn.uppservice.model.Genre;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;

import java.util.List;

@Service
public class AddGenresToForm implements TaskListener {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData formData = delegateTask
                .getExecution()
                .getProcessEngineServices()
                .getFormService()
                .getTaskFormData(delegateTask.getId());

        FormField genresField = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("FormField_genres"))
                .findFirst()
                .get();

        MultiSelectFormFieldType fieldType = (MultiSelectFormFieldType) genresField.getType();

        List<Genre> genres = genreRepository.findAll();
        genres.forEach(genre -> fieldType.getValues().put(genre.getId().toString(), genre.getName()));
    }
}
