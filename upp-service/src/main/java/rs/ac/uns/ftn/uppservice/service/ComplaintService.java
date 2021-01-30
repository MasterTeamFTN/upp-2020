package rs.ac.uns.ftn.uppservice.service;

import rs.ac.uns.ftn.uppservice.dto.response.ComplaintDto;
import rs.ac.uns.ftn.uppservice.model.BoardMember;
import rs.ac.uns.ftn.uppservice.model.Complaint;

import java.util.List;

public interface ComplaintService {

    Complaint findById(Long id);

    List<ComplaintDto> getAll();

    Complaint save(Complaint complaint);

    Complaint updateBoardMemberDecision(BoardMember boardMember, Complaint complaint);
}
