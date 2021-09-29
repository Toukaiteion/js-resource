package com.hseea.jsresource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hseea.jsresource.constant.Constant;
import com.hseea.jsresource.entity.Word;
import com.hseea.jsresource.service.SearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public List<Word> fuzzySearch(String word){
        //搜索请求
        SearchRequest searchRequest = new SearchRequest(Constant.WORD_INDEX);
        //搜索源信息构建器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询匹配构建器
        QueryBuilder matchQueryBuilder = new MultiMatchQueryBuilder(word, Constant.SPELL_COMMON, Constant.SPELL_FULL);

        //高亮构造器创建
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置高亮属性
        HighlightBuilder.Field highlightTitleOfJp = new HighlightBuilder.Field(Constant.SPELL_COMMON);
        HighlightBuilder.Field highlightTitleOfCharactersSpelling = new HighlightBuilder.Field(Constant.SPELL_FULL);
        //属性的高亮类型
        highlightTitleOfJp.highlighterType("unified");
        highlightTitleOfCharactersSpelling.highlighterType("unified");
        highlightTitleOfJp.preTags("<strong>").postTags("</strong>");
        highlightTitleOfCharactersSpelling.preTags("strong").postTags("</strong>");
        highlightBuilder.field(highlightTitleOfJp);
        highlightBuilder.field(highlightTitleOfCharactersSpelling);

        //设置高亮和查询表达式
        sourceBuilder.highlighter(highlightBuilder);
        sourceBuilder.query(matchQueryBuilder).size(10);
        //将源信息构建器作为搜索请求的源
        searchRequest.source(sourceBuilder);
        List<Word> ret = new ArrayList<>();
        Word retJson = new Word();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for(SearchHit sh :searchHits) {
                retJson = JSONObject.parseObject(sh.getSourceAsString(),Word.class);
                Map<String, HighlightField> highlightFields = sh.getHighlightFields();
                HighlightField hightlightOfJp = highlightFields.get(Constant.SPELL_COMMON);
                HighlightField hightlightOfCharactersSpelling = highlightFields.get(Constant.SPELL_FULL);

                if(hightlightOfJp!=null) {
                    Text[] fragmentsOfJp = hightlightOfJp.fragments();
                    retJson.setWordSpellCommon(fragmentsOfJp[0].string());
                }
                if(hightlightOfCharactersSpelling!=null) {
                    Text[] fragmentsOfCharactersSpelling = hightlightOfCharactersSpelling.fragments();
                    retJson.setWordSpellCommon(fragmentsOfCharactersSpelling[0].string());
                }
                ret.add(retJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
