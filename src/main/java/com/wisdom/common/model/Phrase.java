package com.wisdom.common.model;

public class Phrase implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String symbol;
    private String phrase;
    private Integer position;
    private Integer hit;
    private Integer length;


 
    public Phrase() {
        super();
    }
 
    public Phrase(Integer id, String symbol, String phrase, Integer position, Integer hit, Integer length) {
        super();
        this.id = id;
        this.symbol = symbol;
        this.phrase = phrase;
        this.position = position;
        this.hit = hit;
        this.length = length;

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

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
 
    
    
    
}