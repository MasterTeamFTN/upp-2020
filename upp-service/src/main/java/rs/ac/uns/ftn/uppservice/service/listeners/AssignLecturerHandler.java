package rs.ac.uns.ftn.uppservice.service.listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.service.UserService;

@Component
public class AssignLecturerHandler implements TaskListener {

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        User chiefEditor = userService.getLecturer();
        delegateTask.setAssignee(chiefEditor.getUsername());
    }
}
