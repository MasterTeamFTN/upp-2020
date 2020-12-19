package rs.ac.uns.ftn.uppservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.service.ConfirmationTokenService;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String getProcessInstanceId(String token) {
        ConfirmationToken tokenObj = confirmationTokenRepository.findByToken(token);

        if (tokenObj == null) {
            throw new ResourceNotFoundException("Token with this value doesn't exist!");
        }

        return tokenObj.getProcessInstanceId();
    }
}
