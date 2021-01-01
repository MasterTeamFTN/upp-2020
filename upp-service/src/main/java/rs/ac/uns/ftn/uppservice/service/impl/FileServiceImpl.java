package rs.ac.uns.ftn.uppservice.service.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uppservice.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path PAPERS_LOCATION = Paths.get("src/main/resources/papers");

    @Override
    public File saveFile(MultipartFile file) throws IOException {
        /**
         * Create user subfolders where to keep pdf
         */

        Files.copy(file.getInputStream(), this.PAPERS_LOCATION.resolve(file.getOriginalFilename()));
        File convertedFile = new File(this.PAPERS_LOCATION.toString(), file.getOriginalFilename());
        FileUtils.writeByteArrayToFile(convertedFile, file.getBytes());
        return convertedFile;

    }

}
