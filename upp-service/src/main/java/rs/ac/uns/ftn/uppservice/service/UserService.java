package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.UserDTO;
import rs.ac.uns.ftn.uppservice.model.ChiefEditor;
import rs.ac.uns.ftn.uppservice.model.Editor;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
    ChiefEditor getChiefEditor();
    Editor getRandomEditor();
}
