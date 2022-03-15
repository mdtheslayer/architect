package ru.tproger.axondemo.domain.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateLifecycle;
import ru.tproger.axondemo.domain.commands.BorrowBookCommand;
import ru.tproger.axondemo.domain.commands.RegisterBookCommand;
import ru.tproger.axondemo.domain.commands.ReturnBookCommand;
import ru.tproger.axondemo.domain.events.BookBorrowedEvent;
import ru.tproger.axondemo.domain.events.BookRegisteredEvent;
import ru.tproger.axondemo.domain.events.BookReturnedEvent;


import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Book {
    @AggregateIdentifier
    private String bookId;
    private Integer amount;
    private Set<String> tenants;

    @CommandHandler
    public Book(RegisterBookCommand command) {

        if (command.getAmount() <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        apply(new BookRegisteredEvent(command.getBookId(), command.getTitle(), command.getDescription(), command.getAmount()));
    };

    @CommandHandler
    public void handle(BorrowBookCommand command) {


        if (amount < 0)
            throw new IllegalArgumentException("Book out of stock");
        if (tenants.contains(command.getFullName()))
            throw new IllegalArgumentException("Book already borrowed by this person");
        apply(new BookBorrowedEvent(command.getBookId(), command.getFullName()));
    };

    @CommandHandler
    public void handle(ReturnBookCommand command) {


        if (!tenants.contains(command.getFullName()))
            throw new IllegalArgumentException("Book must be returned by person who has borrowed it");
        apply(new BookReturnedEvent(command.getBookId(), command.getFullName()));
    };

    @EventSourcingHandler
    public void on(BookRegisteredEvent event) {


        bookId = event.getBookId();
        amount = event.getAmount();
        tenants = new HashSet<>();
    };

    @EventSourcingHandler
    public void on(BookBorrowedEvent event) {


        amount--;
        tenants.add(event.getFullName());
    };

    @EventSourcingHandler
    public void on(BookReturnedEvent event) {


        amount++;
        tenants.remove(event.getFullName());
    };

}
