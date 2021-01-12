package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.response.UserFileDto;
import rs.ac.uns.ftn.uppservice.model.User;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Path PAPERS_LOCATION = Paths.get("src/main/resources/papers");
    private final static Logger LOGGER = Logger.getLogger(FileServiceImpl.class.getName());

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final RuntimeService runtimeService;
    private final IdentityService identityService;

    @Override
    public UserFileDto saveFile(String taskId, MultipartFile file) throws IOException {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String fileName = file.getOriginalFilename();
        User user = userRepository.findByUsername(task.getAssignee());
        Path destinationPath;

        if (!isNull(user)) {
            destinationPath = Paths.get(PAPERS_LOCATION.toString(), user.getUsername());
            user.getRegistrationPapers().add(destinationPath.resolve(fileName).toString());
            user = userRepository.save(user);
        } else {
            destinationPath = Paths.get(PAPERS_LOCATION.toString());
        }
        File destinationFile = new File(destinationPath.toString());

        if (!destinationFile.exists()) {
            Files.createDirectories(destinationPath);
            LOGGER.info(String.join(" ", new String[]{"Directory ", destinationFile.getName(), " is created."}));
        }


        Files.copy(file.getInputStream(), destinationPath.resolve(fileName));
        File convertedFile = new File(destinationPath.toString(), fileName);
        FileUtils.writeByteArrayToFile(convertedFile, file.getBytes());


        return new UserFileDto(user, convertedFile);
    }


}
