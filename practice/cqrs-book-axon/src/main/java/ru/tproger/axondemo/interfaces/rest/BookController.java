package ru.tproger.axondemo.interfaces.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tproger.axondemo.domain.commands.BorrowBookCommand;
import ru.tproger.axondemo.domain.commands.RegisterBookCommand;
import ru.tproger.axondemo.domain.commands.ReturnBookCommand;
import ru.tproger.axondemo.domain.projections.BookView;
import ru.tproger.axondemo.domain.queries.BookQuery;
import ru.tproger.axondemo.domain.queries.ListBookQuery;
import ru.tproger.axondemo.interfaces.rest.dto.BorrowBookDto;
import ru.tproger.axondemo.interfaces.rest.dto.RegisterBookDto;
import ru.tproger.axondemo.interfaces.rest.dto.ReturnBookDto;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerBook(@RequestBody RegisterBookDto dto) {
        commandGateway.sendAndWait(
                new RegisterBookCommand(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getAmount())
        );

    }

    @PutMapping("/{bookId}/borrow")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrowBook(@PathVariable String bookId,
                           @RequestBody BorrowBookDto dto) {
        commandGateway.sendAndWait(
                new BorrowBookCommand(bookId, dto.getFullName())
        );
    }

    @PutMapping("/{bookId}/return")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@PathVariable String bookId,
                           @RequestBody ReturnBookDto dto) {
        commandGateway.sendAndWait(
                new ReturnBookCommand(bookId, dto.getFullName())
        );
    }

    @GetMapping("/{bookId}")
    public BookView getBook(@PathVariable String bookId) {
        return queryGateway.query(new BookQuery(bookId), BookView.class).join();
    }

    @GetMapping
    public List<BookView> getBookList(@RequestParam(required = false, defaultValue = "") String title) {
        return queryGateway
                .query(new ListBookQuery(title), ResponseTypes.multipleInstancesOf(BookView.class))
                .join();
    }
}
