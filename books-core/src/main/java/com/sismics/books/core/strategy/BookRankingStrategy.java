package com.sismics.books.core.strategy;

import com.sismics.books.core.dao.jpa.dto.CommonLibraryBookDto;
import java.util.List;

public interface BookRankingStrategy {
    List<CommonLibraryBookDto> getTopBooks();
}