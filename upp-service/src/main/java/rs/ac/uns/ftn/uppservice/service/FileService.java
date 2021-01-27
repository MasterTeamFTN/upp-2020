package rs.ac.uns.ftn.uppservice.service;

import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.response.UserFileDto;

import java.io.File;
import java.io.IOException;

public interface FileService {

    UserFileDto saveFile(String taskId, MultipartFile file) throws IOException;
    UserFileDto saveBook(String username, File file) throws IOException;
    void removeFiles(String directoryName) throws IOException;

}
