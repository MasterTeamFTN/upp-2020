package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.common.mapper.ComplaintMapper;
import rs.ac.uns.ftn.uppservice.dto.response.ComplaintDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.BoardMember;
import rs.ac.uns.ftn.uppservice.model.BoardMemberDecision;
import rs.ac.uns.ftn.uppservice.model.Complaint;
import rs.ac.uns.ftn.uppservice.repository.ComplaintRepository;
import rs.ac.uns.ftn.uppservice.service.ComplaintService;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;

    @Override
    public Complaint findById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint doesn't exist"));
    }

    @Override
    public List<ComplaintDto> getAll() {
        return complaintRepository.findAll().stream().map(complaintMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Complaint save(Complaint complaint) {
        return complaintRepository.save(complaint);
    }


    @Override
    public Complaint updateBoardMemberDecision(BoardMember boardMember, Complaint complaint) {
        Complaint persistedCompliant = complaintRepository.findById(complaint.getId()).orElseThrow(EntityExistsException::new);

        persistedCompliant.getBoardMemberDecisions().stream().forEach(boardMemberDecision -> {
            if(boardMemberDecision.getBoardMember().getUsername().equals(boardMember.getUsername())) {

            }
        });
        return null;
    }
}
