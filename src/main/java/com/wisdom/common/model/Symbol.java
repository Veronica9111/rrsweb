package com.wisdom.common.model;

public class Symbol implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String symbol;
    private String similar_symbol;
    private Integer hit;


 
    public Symbol() {
        super();
    }
 
    public Symbol(Integer id, String symbol, String similar_symbol, Integer hit) {
        super();
        this.symbol = symbol;
        this.similar_symbol = similar_symbol;
        this.hit = hit;

    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSimilar_symbol() {
		return similar_symbol;
	}

	public void setSimilar_symbol(String similar_symbol) {
		this.similar_symbol = similar_symbol;
	}

	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}
    
    

}