package com.sismics.books.core.strategy;

import com.sismics.books.core.dao.jpa.dto.CommonLibraryBookDto;
import java.util.List;

public class BookRankingContext {
    private BookRankingStrategy strategy;

    public void setStrategy(BookRankingStrategy strategy) {
        this.strategy = strategy;
    }

    public List<CommonLibraryBookDto> getTopBooks() {
        return strategy.getTopBooks();
    }
}