package rs.ac.uns.ftn.uppservice.common.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.BoardMemberDecisionDto;
import rs.ac.uns.ftn.uppservice.model.BoardMemberDecision;

@Component
@RequiredArgsConstructor
public class BoardMemberDecisionMapper implements CustomMapper<BoardMemberDecision, BoardMemberDecisionDto> {

    private final BoardMemberMapper boardMemberMapper;

    @Override
    public BoardMemberDecisionDto entityToDto(BoardMemberDecision boardMemberDecision) {
        BoardMemberDecisionDto boardMemberDecisionDto = new BoardMemberDecisionDto();

        boardMemberDecisionDto.setId(boardMemberDecision.getId());
        boardMemberDecisionDto.setBoardMember(boardMemberMapper.entityToDto(boardMemberDecision.getBoardMember()));
        boardMemberDecisionDto.setIsPlagiarized(boardMemberDecision.getIsPlagiarized());

        return boardMemberDecisionDto;
    }
}
