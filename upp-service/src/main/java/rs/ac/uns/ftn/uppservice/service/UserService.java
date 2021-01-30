package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.*;
import rs.ac.uns.ftn.uppservice.model.*;

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
    EditorDto getEditor(Long id);
    BoardMemberDto getBoardMember(Long id);
}
