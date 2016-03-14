package com.wisdom.phrase.service;

import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Candidate;

public interface IPhraseService {
	

	public Map<String, Integer> getCandidatePhrases(String phrase, Integer position1, Integer position2);
	
	public int addCandidate(String value, String type, Integer confidence, Integer invoiceId);
	
	public List<Candidate> getCalculatedCandidatePhrases(String type, Integer invoiceId);	
}
