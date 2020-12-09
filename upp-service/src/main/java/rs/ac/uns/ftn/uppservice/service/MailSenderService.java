package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.model.ConfirmationToken;

public interface MailSenderService {

    void sendRegistrationMail(ConfirmationToken token);
}
