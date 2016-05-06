package com.wisdom.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisdom.common.model.Permission;
import com.wisdom.common.model.Symbol;

public interface SymbolMapper {

	List<Symbol> getSimilarSymbols(String symbol);
}
