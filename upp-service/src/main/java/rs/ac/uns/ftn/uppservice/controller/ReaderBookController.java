package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.uppservice.dto.response.BookDto;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping(value = "/readerBook", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReaderBookController {

    private final BookService bookService;
    private final ReaderService readerService;

    @GetMapping("/{readerId}")
    public ResponseEntity<List<BookDto>> getBooksAssignedToBetaReader(@PathVariable Long readerId) {
        return new ResponseEntity<>(
                bookService.getBooksFromIdsList(readerService.findById(readerId).getIdsOfBooksToComment()),
                HttpStatus.OK);
    }
}
