package com.wisdom.phrase.service;

import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Candidate;

public interface IPhraseService {
	

	public Map<String, Integer> getCandidatePhrases(String phrase);
	
	public int addCandidate(String value, String type, Integer confidence, Integer invoiceId);
	
	public List<Candidate> getCalculatedCandidatePhrases(String type, Integer invoiceId);	
	
	public boolean addPhrase(String phrase, Integer hit);
	
	public boolean isPhraseExist(String phrase);
	
	public boolean updatePhraseHit(String phrase, Integer hit);
}
