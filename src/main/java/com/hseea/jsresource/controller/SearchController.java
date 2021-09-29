package com.hseea.jsresource.controller;

import com.hseea.jsresource.entity.Word;
import com.hseea.jsresource.service.SearchService;
import com.hseea.jsresource.util.ResponseModel;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping
    public ResponseModel<List<Word>> fuzzySearch(@RequestParam("word") String word){
        return new ResponseModel<List<Word>>().success(searchService.fuzzySearch(word));
    }
}
