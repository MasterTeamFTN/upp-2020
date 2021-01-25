package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
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
import java.nio.file.*;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static rs.ac.uns.ftn.uppservice.common.constants.Constants.REQUESTED_MEMBER;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Path PAPERS_LOCATION = Paths.get("src/main/resources/papers");
    private final static Logger LOGGER = Logger.getLogger(FileServiceImpl.class.getName());

    private final UserRepository userRepository;
    private final RuntimeService runtimeService;

    @Override
    public void saveFile(String username, String processInstanceId, File file, boolean isRegistration) throws IOException {
        User user = userRepository.findByUsername(username);
        Path destinationPath;

        if (!isNull(user)) {
            destinationPath = Paths.get(PAPERS_LOCATION.toString(), user.getUsername());
            if(isRegistration) {
                user.getRegistrationPapers().add(destinationPath.resolve(file.getName()).toString());
                user = userRepository.save(user);
                runtimeService.setVariable(processInstanceId, REQUESTED_MEMBER, user);
            }
        } else {
            destinationPath = Paths.get(PAPERS_LOCATION.toString());
        }
        File destinationFile = new File(destinationPath.toString());

        if (!destinationFile.exists()) {
            Files.createDirectories(destinationPath);
            LOGGER.info(String.join(" ", new String[]{"Directory ", destinationFile.getName(), " is created."}));
        }

        Files.copy(Paths.get(file.getPath()), destinationPath.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }

//    @Override
//    public UserFileDto saveBook(String username, File file) throws IOException {
//        User user = userRepository.findByUsername(username);
//        Path destinationPath;
//
//        if (!isNull(user)) {
//            destinationPath = Paths.get(PAPERS_LOCATION.toString(), user.getUsername());
//        } else {
//            destinationPath = Paths.get(PAPERS_LOCATION.toString());
//        }
//        File destinationFile = new File(destinationPath.toString());
//
//        if (!destinationFile.exists()) {
//            Files.createDirectories(destinationPath);
//            LOGGER.info(String.join(" ", new String[]{"Directory ", destinationFile.getName(), " is created."}));
//        }
//
//        Files.copy(Paths.get(file.getPath()), destinationPath.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
//
//        return new UserFileDto(user, file);
//    }

    @Override
    public void removeFiles(String directoryName) throws IOException {
        FileUtils.cleanDirectory(new File(Paths.get(PAPERS_LOCATION.toString(), directoryName).toString()));
    }


}
