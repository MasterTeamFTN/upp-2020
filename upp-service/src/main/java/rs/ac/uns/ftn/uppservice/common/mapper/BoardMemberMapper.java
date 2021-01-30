package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.BoardMemberDto;
import rs.ac.uns.ftn.uppservice.model.BoardMember;

@Component
public class BoardMemberMapper implements CustomMapper<BoardMember, BoardMemberDto> {

    @Override
    public BoardMemberDto entityToDto(BoardMember boardMember) {
        BoardMemberDto boardMemberDto = new BoardMemberDto();

        boardMemberDto.setId(boardMember.getId());
        boardMemberDto.setFirstName(boardMember.getFirstName());
        boardMemberDto.setLastName(boardMember.getLastName());
        boardMemberDto.setUsername(boardMember.getUsername());
        boardMemberDto.setEmail(boardMember.getEmail());

        return boardMemberDto;
    }
}
