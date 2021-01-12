package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.WriterDto;
import rs.ac.uns.ftn.uppservice.model.Writer;

@Component
public class WriterMapper implements CustomMapper<Writer, WriterDto> {

    @Override
    public WriterDto entityToDto(Writer writer) {
        WriterDto writerDto = new WriterDto();
        writerDto.setId(writer.getId());
        writerDto.setFirstName(writer.getFirstName());
        writerDto.setLastName(writer.getLastName());
        writerDto.setEmail(writer.getEmail());
        writerDto.setMember(writer.isMember());
        writerDto.setEnabled(writer.isEnabled());
        writerDto.setRegistrationPapers(writer.getRegistrationPapers());
        return writerDto;
    }
}
