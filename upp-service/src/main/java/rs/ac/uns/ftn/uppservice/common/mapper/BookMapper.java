package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.BetaReaderCommentDto;
import rs.ac.uns.ftn.uppservice.dto.response.BookDto;
import rs.ac.uns.ftn.uppservice.model.Book;

import java.util.stream.Collectors;

@Component
public class BookMapper implements CustomMapper<Book, BookDto> {

    @Override
    public BookDto entityToDto(Book book) {
        BookDto bookDto = new BookDto();

        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthorsName(book.getWriter().getFirstName() + " " + book.getWriter().getLastName());
        bookDto.setGenre(book.getGenre().getName());
        bookDto.setIsPublished(book.getIsPublished());
        bookDto.setIsPlagiarized(book.getIsPlagiarized());
        bookDto.setIsRejected(book.getIsRejected());
        bookDto.setProcessInstanceId(book.getProcessInstanceId());
        bookDto.setCityCountry(book.getCityCountry());
        bookDto.setHandwritePath(book.getHandwritePath());
        bookDto.setJurisdiction(book.getJurisdiction().getLabel());

        bookDto.setBetaReadersComments(book.getBetaReadersComments()
                .stream()
                .map(betaReaderComment ->
                        new BetaReaderCommentDto(betaReaderComment.getId(),
                                betaReaderComment.getComment(),
                                betaReaderComment.getReader().getUsername())).collect(Collectors.toSet()));

        return bookDto;
    }
}