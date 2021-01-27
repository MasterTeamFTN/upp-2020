package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;
import rs.ac.uns.ftn.uppservice.repository.ConfirmationTokenRepository;
import rs.ac.uns.ftn.uppservice.service.ConfirmationTokenService;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String getProcessInstanceId(String token) {
        ConfirmationToken tokenObj = confirmationTokenRepository.findByToken(token);

        if (tokenObj == null) {
            throw new ResourceNotFoundException("Token with this value doesn't exist!");
        }

        return tokenObj.getProcessInstanceId();
    }
}
