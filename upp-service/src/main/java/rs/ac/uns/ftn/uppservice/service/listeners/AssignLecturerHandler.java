package rs.ac.uns.ftn.uppservice.service.listeners;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.service.UserService;

@Component
@RequiredArgsConstructor
public class AssignLecturerHandler implements TaskListener {

    private final UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        User chiefEditor = userService.getLecturer();
        delegateTask.setAssignee(chiefEditor.getUsername());
    }
}
