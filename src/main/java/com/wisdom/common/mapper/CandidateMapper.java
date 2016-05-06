package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Candidate;

public interface CandidateMapper {
	
	public int addCandidate(@Param("value")String value, @Param("type")String type, @Param("confidence")Integer confidence, @Param("invoice_id")Integer invoiceId);
	
	public List<Candidate> getCandidatesByTypeAndInvoiceId(@Param("type")String type, @Param("invoice_id")Integer invoiceId);
}
