package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Phrase;

public interface PhraseMapper {

	List<Phrase> getPhraseBySymbol(String symbol);
	
	int addPhraseWithSymbol(@Param("phrase")String phrase, @Param("symbol")String symbol, @Param("hit")Integer hit, @Param("length")Integer length, @Param("position")Integer position);
	
	List<Phrase> getPhrase(String phrase);
	
	int updatePhraseHit(@Param("phrase")String phrase, @Param("hit")Integer hit);
}
