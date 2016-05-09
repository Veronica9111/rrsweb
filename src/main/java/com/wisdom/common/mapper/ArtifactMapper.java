package com.wisdom.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface ArtifactMapper {

    void addArtifact(@Param("invoice_id")Integer invoiceId, @Param("supplier_name")String supplierName, @Param("type")String type, @Param("tax")Double tax, @Param("amount")Double amount,@Param("number")Integer number,@Param("is_fa")Integer isFa);
}
