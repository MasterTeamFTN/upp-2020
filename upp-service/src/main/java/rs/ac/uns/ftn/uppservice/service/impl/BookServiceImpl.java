package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.common.mapper.BookMapper;
import rs.ac.uns.ftn.uppservice.dto.request.FormSubmissionDto;
import rs.ac.uns.ftn.uppservice.dto.response.BookDto;
import rs.ac.uns.ftn.uppservice.exception.exceptions.ResourceNotFoundException;
import rs.ac.uns.ftn.uppservice.model.*;
import rs.ac.uns.ftn.uppservice.repository.BookRepository;
import rs.ac.uns.ftn.uppservice.repository.GenreRepository;
import rs.ac.uns.ftn.uppservice.repository.UserRepository;
import rs.ac.uns.ftn.uppservice.service.BookService;
import rs.ac.uns.ftn.uppservice.service.MailSenderService;
import rs.ac.uns.ftn.uppservice.service.ReaderService;
import rs.ac.uns.ftn.uppservice.service.UserService;
import rs.ac.uns.ftn.uppservice.util.SetUtils;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final RuntimeService runtimeService;
    private final ReaderService readerService;

    private final BookMapper bookMapper;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    @Override
    public Book submitInitForm(List<FormSubmissionDto> formData, String processInstanceId) {
        Book book = createNewBook(formData, processInstanceId);
        ChiefEditor chiefEditor = userService.getChiefEditor();
        mailSenderService.sendChiefEditorNewBookNotification(chiefEditor, book);

        runtimeService.setVariable(processInstanceId, "firstReviewAssignee", chiefEditor.getUsername());
        runtimeService.setVariable(processInstanceId, "book", book);

        return book;
    }

    private Book createNewBook(List<FormSubmissionDto> formData, String processInstanceId) {
        Book book = new Book();
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (FormSubmissionDto field : formData) {
            if(field.getFieldId().equals("FormField_title")) book.setTitle((String) field.getFieldValue());
            if(field.getFieldId().equals("FormField_genre")) {
                Genre genre = genreRepository.findById(Long.parseLong((String) field.getFieldValue()))
                        .orElseThrow(() -> new ResourceNotFoundException("Genre with name '" + field.getFieldValue() + "' doesn't exist"));
                book.setGenre(genre);
            }
            if(field.getFieldId().equals("FormField_synopsis")) book.setSynopsis((String) field.getFieldValue());

        }

        book.setWriter(writer);
        book.setIsPublished(false);
        book.setIsReviewSubmitted(false);
        book.setProcessInstanceId(processInstanceId);
        book = bookRepository.save(book);

        return book;
    }

    @Override
    public void rejectFirstReview(List<FormSubmissionDto> formData, Book book) {
        String reason = "";

        for (FormSubmissionDto field : formData) {
            if (field.getFieldId().equals("FormField_reason")) reason = (String) field.getFieldValue();
        }

        mailSenderService.sendWriterRejectBook(book, reason);

        Book b = this.findById(book.getId());
        b.setIsRejected(true);
        bookRepository.save(b);
    }

    @Override
    public void rejectAfterTimeOut(Book book) {
        mailSenderService.notifyWriterFullBookTimedOut(book);
        Book b = this.findById(book.getId());
        b.setIsRejected(true);
        bookRepository.save(b);
    }

    @Override
    public void reject(Long bookId) {
        Book b = this.findById(bookId);
        b.setIsRejected(true);
        bookRepository.save(b);
    }

    @Override
    public void markBookAsPlagiarised(Book book) {
        book.setIsPlagiarized(true);
        bookRepository.save(book);
    }

    @Override
    public Book saveFullBookData(List<FormSubmissionDto> formData, Long bookId) {
        Book book = findById(bookId);

        for (FormSubmissionDto field : formData) {
            if (field.getFieldId().equals("FormField_cowriters")) {
                List<String> cowriters = Arrays.asList(((String) field.getFieldValue()).split(";"));
                book.setCoWriters(SetUtils.fromListToSet(cowriters));
            }

            if (field.getFieldId().equals("FormField_keywords")) {
                List<String> keywords = Arrays.asList(((String) field.getFieldValue()).split(";"));
                book.setKeywords(SetUtils.fromListToSet(keywords));
            }

            if (field.getFieldId().equals("FormField_year")) book.setYear((Integer) field.getFieldValue());
            if (field.getFieldId().equals("FormField_cityCountry")) book.setCityCountry((String) field.getFieldValue());
            if (field.getFieldId().equals("FormField_numOfPages")) book.setNumOfPages((Integer) field.getFieldValue());
        }

        book = bookRepository.save(book);
        return book;
    }

    @Override
    public void addBetaReadersComments(Long bookId, String readerUsername, String comment) {
        Book book = this.findById(bookId);
        Reader reader = readerService.findByUsername(readerUsername);

        BetaReaderComment readersComment = new BetaReaderComment();
        readersComment.setReader(reader);
        readersComment.setComment(comment);
        readersComment.setBook(book);

        book.getBetaReadersComments().add(readersComment);
        bookRepository.save(book);
    }

    @Override
    public Suggestion addLecturersComments(Long bookId, String comment) {
        Book book = this.findById(bookId);
        Lecturer lecturer = (Lecturer) userRepository.findLecturer().get();

        Set<String> foundErrors = new HashSet<>();
        foundErrors.add(comment);

        Suggestion suggestion = new Suggestion();
        suggestion.setLecturer(lecturer);
        suggestion.setBook(book);
        suggestion.setFoundErrors(foundErrors);

        book.getSuggestions().add(suggestion);
        bookRepository.save(book);

        return suggestion;
    }

    @Override
    public Suggestion addChiefEditorsComments(Long bookId, String comment) {
        Book book = this.findById(bookId);
        ChiefEditor chiefEditor = (ChiefEditor) userRepository.findChiefEditor().get();

        Set<String> foundErrors = new HashSet<>();
        foundErrors.add(comment);

        Suggestion suggestion = new Suggestion();
        suggestion.setChiefEditor(chiefEditor);
        suggestion.setBook(book);
        suggestion.setFoundErrors(foundErrors);

        book.getSuggestions().add(suggestion);
        bookRepository.save(book);

        return suggestion;
    }

    @Override
    public void publish(Long bookId) {
        Book book = this.findById(bookId);
        book.setPublisher("UPP FTN Publisher");
        book.setIsbn(UUID.randomUUID().toString());
        book.setIsPublished(true);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Book registerReviewSubmission(Book book) {
        Book bookFromDatabase = bookRepository.findById(book.getId()).orElseThrow(EntityNotFoundException::new);
        bookFromDatabase.setIsReviewSubmitted(true);
        bookRepository.save(bookFromDatabase);

        return bookFromDatabase;
    }

    @Override
    public List<BookDto> getMyBooks(String username) {
        return bookRepository.findAllByWriterUsername(username)
                .stream()
                .map(bookMapper::entityToDto).collect(Collectors.toList());
    }

}
