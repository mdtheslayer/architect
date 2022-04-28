package com.example.hateoasexample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.springframework.hateoas.
public class Ad implements {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {

        BUY,

        SELL

    }

    @Column(nullable = false)
    private BigInteger amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public enum Currency {

        USD,

        EUR

    }

    @Column(nullable = false)
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    private Location location;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {

        @Column(nullable = false)
        private String city;

        private String area;

    }

    private String comment;

    @Lob
    private LocalDateTime publishedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    public enum Status {

        NEW,

        PUBLISHED,

        EXPIRED

    }
}
/*
    public Ad publish() {
        if (status == Status.NEW) {
            publishedAt = LocalDateTime.now();
            status = Status.PUBLISHED;
        }
        else {
            throw new InvalidAdStateTransitionException("Ad is already published");
        }
        return this;
    }

    public Ad expire() {
        if (status == Status.PUBLISHED) {
            status = Status.EXPIRED;
        }
        else {
            throw new InvalidAdStateTransitionException(
                    "Ad can be expire only when it is in the " + Status.PUBLISHED + " state");
        }
        return this;
    }

}
 */

