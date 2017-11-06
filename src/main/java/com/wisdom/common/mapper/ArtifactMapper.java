package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Artifact;

public interface ArtifactMapper {

    void addArtifact(@Param("invoice_id")Integer invoiceId, @Param("supplier_name")String supplierName, @Param("type")String type, @Param("tax")Double tax, @Param("amount")Double amount,@Param("number")Integer number,@Param("is_fa")Integer isFa, @Param("is_accurate")Integer isAccurate);

    //新增方法
    public List<Artifact> getArtifactByInvoiceId(@Param("invoice_id")Integer invoiceId);

	public Artifact getAtifactByArtifactId(@Param("artifact_id")Integer artifact_id);

	public Integer updateArtifactByArtifactId(@Param("artifact_id")Integer artifact_id, @Param("supplier_name")String supplierName, @Param("type")String type, @Param("amount")Double amount,@Param("tax")Double tax,
			@Param("number")Integer number, @Param("is_fa")Integer is_fa);
}
