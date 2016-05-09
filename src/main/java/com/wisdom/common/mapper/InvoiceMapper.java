package com.wisdom.common.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Invoice;

public interface InvoiceMapper {

	List<Invoice> getAllInvoices();
	
	void addInvoice(@Param("id")String id, @Param("name")String name, @Param("created_time")Timestamp created_time, @Param("priority")Integer priority, @Param("path")String path, @Param("company")String company, @Param("invoice_id")long invoiceId, @Param("company_id") long companyId);
	
	List<Invoice> getInvoicesByCompany(@Param("company")String company);
	
	List<Invoice>getInvoicesByStatus(@Param("status")String status);
	
	List<Invoice>getUnexportedInvoices();
	
	Invoice getInvoiceByUserAndStatus(@Param("uid")Integer uid, @Param("status")String status);
	
	void setInvoiceExported(@Param("id")String id);
	
	void updateInvoiceStatus(@Param("id")String id, @Param("status")String status);
	
	void updateInvoiceOwner(@Param("id")String id, @Param("uid")Integer uid);
	
	void addModifyInvoiceRecord(@Param("uid")Integer uid, @Param("invoice_id")String invoice_id, @Param("created_time")Timestamp modified_time, @Param("action") String action);
	
	Invoice getInvoiceForUserByStatus(@Param("status")String status);
	
	void increaseInvoicePriority(@Param("id")String id);
	
	void decreaseInvoicePriority(@Param("id")String id);
	
	Invoice getInvoiceById(@Param("id")String id);
	
	void deleteInvoice(@Param("invoice_id")long invoiceId);
	
	Invoice getInvoiceByInvoiceId(@Param("invoice_id") long invoiceId);
	
	void updateInvoiceStatusWithInvoiceId(@Param("invoice_id")Integer invoiceId, @Param("status")String status);
}

