package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.PdfResourceDto;
import rs.ac.uns.ftn.uppservice.model.Book;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String saveFile(String username, String processInstanceId, File file, boolean isRegistration) throws IOException;

    //    UserFileDto saveBook(String username, File file) throws IOException;
    void removeFiles(String directoryName) throws IOException;

    PdfResourceDto getHandwrite(Book book);
}
