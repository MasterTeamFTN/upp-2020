package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.dto.response.BookDto;
import rs.ac.uns.ftn.uppservice.model.Book;

@Component
public class BookMapper implements CustomMapper<Book, BookDto> {

    @Override
    public BookDto entityToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthorsName(book.getWriter().getFirstName() + " " + book.getWriter().getLastName());
        bookDto.setGenre(book.getGenre().getName());
        bookDto.setIsPublished(book.getIsPublished());
        bookDto.setProcessInstanceId(book.getProcessInstanceId());
        bookDto.setIsReviewSubmitted(book.getIsReviewSubmitted());
        bookDto.setCityCountry(book.getCityCountry());

        return bookDto;
    }
}