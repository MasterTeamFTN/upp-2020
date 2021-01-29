package rs.ac.uns.ftn.uppservice.service.delegates.file;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.common.constants.Constants;
import rs.ac.uns.ftn.uppservice.model.Book;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;
import java.io.IOException;

import static rs.ac.uns.ftn.uppservice.common.constants.Constants.WORK_COUNT;

@Component
@RequiredArgsConstructor
public class FileUploadValidateDelegate implements JavaDelegate {

    private final FileService fileService;
    private final TaskService taskService;

    @Override
    public void execute(DelegateExecution execution) throws IOException {

        File pdfFile = (File) execution.getVariable(Constants.SUBMIT_FILE_DATA);
        String tempPath = (String) execution.getVariable(Constants.TEMP_FILE_PATH);
        Task task = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();

        fileService.saveFile(task.getAssignee(), execution.getProcessInstanceId(), pdfFile, true);

        File tempFile = new File(tempPath);
        tempFile.delete();

        int workCount = 1;
        if (execution.getVariable(WORK_COUNT) != null) {
            workCount = (int) execution.getVariable(WORK_COUNT);
            workCount++;
        }
        execution.setVariable(WORK_COUNT, workCount);
    }
}