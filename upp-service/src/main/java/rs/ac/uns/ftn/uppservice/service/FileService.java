package rs.ac.uns.ftn.uppservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.dto.response.UserFileDto;

import java.io.File;
import java.io.IOException;

@Service
public interface FileService {

    UserFileDto saveFile(String taskId, MultipartFile file) throws IOException;
    UserFileDto saveBook(String taskId, MultipartFile file) throws IOException;

}
