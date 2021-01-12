package rs.ac.uns.ftn.uppservice.common.mapper;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.model.BoardMember;

@Component
@RequiredArgsConstructor
public class CamundaUserMapper implements CustomMapper<BoardMember, UserEntity> {

    private final IdentityService identityService;

    @Override
    public UserEntity entityToDto(BoardMember boardMember) {
        UserEntity user = new UserEntity();
//        User user = identityService.newUser(boardMember.getId().toString());
        user.setEmail(boardMember.getEmail());
        user.setFirstName(boardMember.getFirstName());
        user.setLastName(boardMember.getLastName());
        user.setId(boardMember.getId().toString());
        
        return user;
    }
}
