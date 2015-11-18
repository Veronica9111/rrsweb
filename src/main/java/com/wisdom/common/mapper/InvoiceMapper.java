package com.wisdom.common.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Invoice;

public interface InvoiceMapper {

	List<Invoice> getAllInvoices();
	
	void addInvoice(@Param("id")String id, @Param("name")String name, @Param("created_time")Timestamp created_time, @Param("path")String path, @Param("company")String company);
	
	List<Invoice> getInvoicesByCompany(@Param("company")String company);
	
	List<Invoice>getInvoicesByStatus(@Param("status")String status);
	
	List<Invoice>getUnexportedInvoices();
	
	void setInvoiceExported(@Param("id")String id);
	
	void updateInvoiceStatus(@Param("id")String id, @Param("status")String status);
	
	void updateInvoiceOwner(@Param("id")String id, @Param("uid")Integer uid);
	
	void addModifyInvoiceRecord(@Param("uid")Integer uid, @Param("invoice_id")String invoice_id, @Param("created_time")Timestamp modified_time, @Param("action") String action);
}

