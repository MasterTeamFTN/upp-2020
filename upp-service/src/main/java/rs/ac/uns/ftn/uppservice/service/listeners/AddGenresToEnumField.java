package rs.ac.uns.ftn.uppservice.service.listeners;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.model.Genre;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddGenresToEnumField implements TaskListener {

    private final GenreRepository genreRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData formData = delegateTask
                .getExecution()
                .getProcessEngineServices()
                .getFormService()
                .getTaskFormData(delegateTask.getId());

        FormField genreField = formData.getFormFields().stream()
                .filter(formField -> formField.getId().equals("FormField_genre"))
                .findFirst()
                .get();

        EnumFormType fieldType = (EnumFormType) genreField.getType();

        List<Genre> genres = genreRepository.findAll();
        genres.forEach(genre -> fieldType.getValues().put(genre.getId().toString(), genre.getName()));
    }
}
