package com.hseea.jsresource.service;

import com.hseea.jsresource.entity.Word;

import java.util.List;

public interface SearchService {
    List<Word> fuzzySearch(String word);
}
