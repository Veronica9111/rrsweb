package com.wisdom.invoice.service;

import java.util.List;
import java.util.Map;

import com.wisdom.common.model.Invoice;

public interface IInvoiceService {

	public List<Map<String, String>>  getAllInvoices();
}
