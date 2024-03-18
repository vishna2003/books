package com.sismics.books.core.strategy;

import com.sismics.books.core.dao.jpa.CommonLibraryBookDao;
import com.sismics.books.core.dao.jpa.dto.CommonLibraryBookDto;
import java.util.List;

public class NumberOfRatingsStrategy implements BookRankingStrategy {

    private CommonLibraryBookDao bookDao;

    public NumberOfRatingsStrategy(CommonLibraryBookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<CommonLibraryBookDto> getTopBooks() {
        // Implement logic to fetch top 10 books based on the number of ratings
        // Placeholder for actual implementation
        return bookDao.findTopByNumberOfRatings(10);
    }
}