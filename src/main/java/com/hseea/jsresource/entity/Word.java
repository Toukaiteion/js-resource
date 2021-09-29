package com.hseea.jsresource.entity;

import lombok.Data;

@Data
public class Word {
    private int wordId;
    private String wordSpellCommon;
    private String wordSpellFull;
    private String feature;
    private String explanation;
    private String exampleSentence;
    private String exampleSentenceCn;
    private Integer proficiency;

}
