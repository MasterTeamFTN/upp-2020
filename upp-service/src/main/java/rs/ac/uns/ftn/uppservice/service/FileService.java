package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.UserFileDto;

import java.io.File;
import java.io.IOException;

public interface FileService {

    void saveFile(String taskId, String processInstanceId, File file, boolean isRegistration) throws IOException;
//    UserFileDto saveBook(String username, File file) throws IOException;
    void removeFiles(String directoryName) throws IOException;

}
