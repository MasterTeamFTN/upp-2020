package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.UserDTO;
import rs.ac.uns.ftn.uppservice.model.ChiefEditor;
import rs.ac.uns.ftn.uppservice.model.Editor;
import rs.ac.uns.ftn.uppservice.dto.response.WriterDto;
import rs.ac.uns.ftn.uppservice.model.Lecturer;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
    ChiefEditor getChiefEditor();
    Lecturer getLecturer();
    Editor getRandomEditor();
    WriterDto getWriter(Long id);
}
