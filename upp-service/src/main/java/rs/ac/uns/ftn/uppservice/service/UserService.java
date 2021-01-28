package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.ReaderDto;
import rs.ac.uns.ftn.uppservice.dto.response.UserDTO;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.dto.response.WriterDto;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
    ChiefEditor getChiefEditor();
    Lecturer getLecturer();
    Editor getRandomEditor();
    WriterDto getWriter(Long id);
    ReaderDto getReader(Long id);
}
