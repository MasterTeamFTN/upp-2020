package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.common.mapper.ReaderMapper;
import rs.ac.uns.ftn.uppservice.common.mapper.WriterMapper;
import rs.ac.uns.ftn.uppservice.dto.response.ReaderDto;
import rs.ac.uns.ftn.uppservice.dto.response.UserDTO;
import rs.ac.uns.ftn.uppservice.dto.response.WriterDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ApiRequestException;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WriterMapper writerMapper;
    private final ReaderMapper readerMapper;

    @Override
    public UserDTO findById(Long id) throws ApiRequestException {
        try {
            User user = userRepository.findById(id).get();
            return new UserDTO(user);
        } catch (NoSuchElementException e) {
            throw new ApiRequestException("User with id '" + id + "' doesn't exist.");
        }
    }

    @Override
    public UserDTO findByUsername(String username) throws ApiRequestException {
        try {
            User user = userRepository.findByUsername(username);
            return new UserDTO(user);
        } catch (UsernameNotFoundException e) {
            throw new ApiRequestException("User with username '" + username + "' doesn't exist.");
        }
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public ChiefEditor getChiefEditor() {
        return (ChiefEditor) userRepository.findChiefEditor()
                .orElseThrow(() -> new ResourceNotFoundException("Chief editor doesn't exist"));
    }

    @Override
    public Lecturer getLecturer() {
        return (Lecturer) userRepository.findLecturer()
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer doesn't exist"));
    }

    @Override
    public Editor getRandomEditor() {
        List<User> editors = userRepository.findAllEditors();
        Random random = new Random();
        return (Editor) editors.get(random.nextInt(editors.size()));
    }

    @Override
    public WriterDto getWriter(Long id) {
        Writer writer = (Writer) userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return writerMapper.entityToDto(writer);
    }

    @Override
    public ReaderDto getReader(Long id) {
        Reader reader = (Reader) userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return readerMapper.entityToDto(reader);
    }
}
