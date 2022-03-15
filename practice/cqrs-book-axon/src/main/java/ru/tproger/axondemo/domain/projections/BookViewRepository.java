package ru.tproger.axondemo.domain.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookViewRepository extends JpaRepository<BookView, String> {
    List<BookView> findByTitleContaining(String title);
}
