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
	  
	  private static Integer searchLimit = 5;



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
	public Map<String, Integer> getCandidatePhrases(String phrase) {
		// TODO Auto-generated method stub
		Map<String, Integer> retMap = new HashMap<>();
		Map<String, Integer> retMap2 = new HashMap<>();
		String result = "";
		Map<String, List<Phrase>> phrasesMap = new HashMap<>();
		for(int i = 0; i < phrase.length() - 1; i++){
			String rightSymbol = phrase.substring(i, i+1);
			List<Phrase> phrases = new ArrayList<>();
			if(!phrasesMap.containsKey(rightSymbol)){
				phrases = phraseMapper.getPhraseBySymbol(rightSymbol);
				phrasesMap.put(rightSymbol, phrases);
			}else{
				phrases = phrasesMap.get(rightSymbol);
			}
			retMap = getPhraseByLength(phrases, rightSymbol, 2);
			String nextSymbol = phrase.substring(i+1, i+2);
			if(isHit(rightSymbol + nextSymbol, retMap)){
				//right and not change
				result += rightSymbol + nextSymbol;
				i++;
			}else{
				//find the most hit one
				String word = "";
				Integer hit = 0;
				for ( Map.Entry<String, Integer> entry : retMap.entrySet() ) {
				    word = entry.getKey();
				    hit = entry.getValue();
				    break;
				}
				if(hit > 10){
					result += word;
					i++;
				}else{
					result += rightSymbol;
				}
				// if it's empty
				if(word.equals("")){
					result += rightSymbol;
				}
			}
		}
		if(result.length() < phrase.length()){
			result += phrase.substring(phrase.length()-1, phrase.length());
		}
		retMap2.put(result, 50);
		
		//Match with reversed order
		String result2 = "";
		Map<String, Integer> phraseMap = new HashMap<>();
		for(int i = phrase.length()-1; i>= 0; i--){
			String rightSymbol = phrase.substring(i,i+1);
			List<Phrase> phrases = new ArrayList<>();
			if(!phrasesMap.containsKey(rightSymbol)){
				phrases = phraseMapper.getPhraseBySymbol(rightSymbol);
				phrasesMap.put(rightSymbol, phrases);
			}else{
				phrases = phrasesMap.get(rightSymbol);
			}
			phraseMap = getInversedPhraseByLength(phrases, rightSymbol, 2);
			if(i == 0){break;}
			String previousSymbol = phrase.substring(i-1,i);
			if(isHit(previousSymbol + rightSymbol, phraseMap)){
				//right and not change
				result2 = previousSymbol + rightSymbol + result2;
				i--;
			}else{
				String word = "";
				Integer hit = 0;
				for ( Map.Entry<String, Integer> entry : phraseMap.entrySet() ) {
				    word = entry.getKey();
				    hit = entry.getValue();
				    break;
				}
				if(hit > 10){
					result2 = word + result2;
					i--;
				}else{
					result2 = rightSymbol + result2;
				}

			}
		}
		if(result2.length() < phrase.length()){
			result2 = phrase.substring(0,1) + result2;
		}
		
		retMap2.put(result2, 50);
		
		List<Integer> wrongPositions = new ArrayList<>();
		if(result.length() == result2.length()){
			for(int i = 0; i < result.length(); i++){
				if(!result.substring(i, i+1).equals(result2.substring(i, i+1))){
					wrongPositions.add(i);
				}
				
			}
		}
		//Fix it again
		String result3 = phrase;
		for(Integer position: wrongPositions){

			Map<String, Integer> beforeMap = new HashMap<>();
			Map<String, Integer> afterMap = new HashMap<>();
			String rightSymbol = "";
			String pSymbol = "";
			Integer pHit = 0;
			String aSymbol = "";
			Integer aHit = 0;
			if(position > 0){
				//with previous character
				rightSymbol = phrase.substring(position-1, position);
				beforeMap = getPhraseByLength(phrasesMap.get(rightSymbol),rightSymbol, 2);

				for ( Map.Entry<String, Integer> entry : beforeMap.entrySet() ) {
				    pSymbol = entry.getKey().substring(1,2);
				    pHit = entry.getValue();
				    break;
				}
			}


			if(position < phrase.length()-1){
				rightSymbol = phrase.substring(position+1, position+2);
				//with after character
				afterMap = getInversedPhraseByLength(phrasesMap.get(rightSymbol),rightSymbol, 2);
				
				for ( Map.Entry<String, Integer> entry : afterMap.entrySet() ) {
				    aSymbol = entry.getKey().substring(0,1);
				    aHit = entry.getValue();
				    break;
				}
			}
			
			String fitSymbol = aSymbol;
			if(pHit > aHit){
				fitSymbol = pSymbol;
			}
			if(position < phrase.length()-1){
				result3 = result3.substring(0,position) + fitSymbol + result3.substring(position + 1);
			}else{
				result3 = result3.substring(0, position) + fitSymbol;
			}
			
		}
		
		retMap2.put(result3, 70);
		
		return retMap2;
	    
	}
	
	
	public boolean isHit(String phrase, Map<String, Integer>retMap){
		if(retMap.containsKey(phrase) && retMap.get(phrase) > 5){
			return true;
		}
		return false;
	}
	
	public Map<String,Integer> getInversedPhraseByLength(List<Phrase> phrases, String rightSymbol, Integer length){
		List<String> retList = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		for(Phrase phrase: phrases){
			Integer end = phrase.getPhrase().indexOf(rightSymbol)+1;
			Integer start = end - length;
			if(start < 0 ){
				continue;
			}
			String cutPhrase = phrase.getPhrase().substring(start, end);
			if(map.containsKey(cutPhrase)){
				map.put(cutPhrase, map.get(cutPhrase) + phrase.getHit());
			}else{
				map.put(cutPhrase, phrase.getHit());
			}
		}
		map = sortByValue(map);
		return map;
	}
	
	public Map<String,Integer> getPhraseByLength(List<Phrase> phrases, String rightSymbol, Integer length){
		List<String> retList = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		for(Phrase phrase: phrases){
			Integer start = phrase.getPhrase().indexOf(rightSymbol);
			Integer end = start + length;
			if(end > phrase.getPhrase().length()){
				continue;
			}
			String cutPhrase = phrase.getPhrase().substring(start, end);
			if(map.containsKey(cutPhrase)){
				map.put(cutPhrase, map.get(cutPhrase) +phrase.getHit());
			}else{
				map.put(cutPhrase, phrase.getHit());
			}
		}
		map = sortByValue(map);
		return map;
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
