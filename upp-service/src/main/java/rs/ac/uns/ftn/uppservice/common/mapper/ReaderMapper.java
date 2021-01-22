package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.ReaderDto;
import rs.ac.uns.ftn.uppservice.model.Reader;

@Component
public class ReaderMapper implements CustomMapper<Reader, ReaderDto> {

    @Override
    public ReaderDto entityToDto(Reader reader) {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(reader.getId());
        readerDto.setFirstName(reader.getFirstName());
        readerDto.setLastName(reader.getLastName());
        readerDto.setUsername(reader.getUsername());
        readerDto.setEnabled(reader.isEnabled());
        readerDto.setEmail(reader.getEmail());
        readerDto.setIsBetaReader(reader.getIsBetaReader());
        readerDto.setPenaltyPoints(reader.getPenaltyPoints());

        return readerDto;
    }
}
