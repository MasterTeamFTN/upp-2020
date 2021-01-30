package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.EditorDto;
import rs.ac.uns.ftn.uppservice.model.Editor;

@Component
public class EditorMapper implements CustomMapper<Editor, EditorDto> {
    @Override
    public EditorDto entityToDto(Editor editor) {
        EditorDto editorDto = new EditorDto();

        editorDto.setId(editor.getId());
        editorDto.setFirstName(editor.getFirstName());
        editorDto.setLastName(editor.getLastName());
        editorDto.setUsername(editor.getUsername());
        editorDto.setEmail(editor.getEmail());

        return editorDto;
    }
}
