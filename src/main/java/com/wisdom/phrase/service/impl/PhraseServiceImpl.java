package com.wisdom.phrase.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.mapper.CandidateMapper;
import com.wisdom.common.mapper.PhraseMapper;
import com.wisdom.common.mapper.SymbolMapper;
import com.wisdom.common.model.Candidate;
import com.wisdom.common.model.Phrase;
import com.wisdom.common.model.Symbol;
import com.wisdom.phrase.service.IPhraseService;


@Service("phraseService")
public class PhraseServiceImpl implements IPhraseService {


	  @Autowired
	  private	PhraseMapper phraseMapper;
	  
	  @Autowired
	  private SymbolMapper symbolMapper;

	  @Autowired
	  private CandidateMapper candidateMapper;



	private static final Logger logger = LoggerFactory
			.getLogger(PhraseServiceImpl.class);
	
	
	public void setPhraseMapper(PhraseMapper phraseMapper) {
		    this.phraseMapper = phraseMapper;
	}

	public void setSymbolMapper(SymbolMapper symbolMapper) {
	    this.symbolMapper = symbolMapper;
	}
	
	public void setCandidateMapper(CandidateMapper candidateMapper){
		this.candidateMapper = candidateMapper;
	}

	@Override
	public Map<String, Integer> getCandidatePhrases(String phrase, Integer position1, Integer position2) {
		// TODO Auto-generated method stub
		String rightSymbol = phrase.substring(position1, position1+1);
		List<Phrase> phrases = phraseMapper.getPhraseBySymbol(rightSymbol);
		String uncertainSymbol = phrase.substring(position2, position2+1);
		List<Symbol> symbols = symbolMapper.getSimilarSymbols(uncertainSymbol);
		//List<String> phrasesList = new ArrayList<>();
		Map<String, Integer> phrasesMap = new HashMap<>();
		for(Phrase elem: phrases){
			phrasesMap.put(elem.getPhrase(), elem.getHit());
		}
		
		Map<String, Integer> result = searchPhrase(rightSymbol, position1, phrase.length(), phrasesMap);
		return result;
	    
	    
	    
	    
	}
	
	public  <K, V extends Comparable<? super V>> Map<K, V> 
    sortByValue( Map<K, V> map )
{
    List<Map.Entry<K, V>> list =
        new LinkedList<Map.Entry<K, V>>( map.entrySet() );
    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
    {
        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
        {
            return (o2.getValue()).compareTo( o1.getValue() );
        }
    } );

    Map<K, V> result = new LinkedHashMap<K, V>();
    for (Map.Entry<K, V> entry : list)
    {
        result.put( entry.getKey(), entry.getValue() );
    }
    
    return result;
}
	
	public  Map<String, Integer> searchPhrase(String character, Integer position, Integer length, Map<String, Integer>phrases){
		Map<String, Integer> result = new HashMap<>();
		for (Map.Entry<String, Integer> entry : phrases.entrySet()) {
			String phrase = entry.getKey();
			Integer index = phrase.indexOf(character);
			Integer left = index - position;
			Integer right = length - position + index;
			if(left >= 0 && right <= phrase.length()){
				String match = phrase.substring(left, right);
				if(!result.containsKey(match)){
					result.put(match, entry.getValue());
				}else{
					result.put(match, result.get(match) + entry.getValue());
				}
			}
		}
		Map<String, Integer> sorted = sortByValue(result);
		return sorted;
	}

	@Override
	public int addCandidate(String value, String type, Integer confidence, Integer invoiceId) {
		
		return candidateMapper.addCandidate(value, type, confidence, invoiceId);
	}

	@Override
	public List<Candidate> getCalculatedCandidatePhrases(String type, Integer invoiceId) {
		// TODO Auto-generat ed method stub
		return candidateMapper.getCandidatesByTypeAndInvoiceId(type, invoiceId);
	}

	@Override
	public boolean addPhrase(String phrase, Integer hit) {
		// TODO Auto-generated method stub
		for(int i = 0; i < phrase.length(); i++){
			phraseMapper.addPhraseWithSymbol(phrase, phrase.substring(i,i+1), hit, phrase.length(), i);
		}
		return true;
	}

	@Override
	public boolean isPhraseExist(String phrase) {
		// TODO Auto-generated method stub
		List<Phrase> list = phraseMapper.getPhrase(phrase);
		if(list.isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean updatePhraseHit(String phrase, Integer hit) {
		// TODO Auto-generated method stub
		phraseMapper.updatePhraseHit(phrase, hit);
		return true;
	}

}
