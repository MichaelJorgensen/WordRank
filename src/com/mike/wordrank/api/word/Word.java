package com.mike.wordrank.api.word;

public abstract class Word {

	private String name;
	private int uses;
	
	public Word(String name, int uses) {
		this.name = name.toLowerCase();
		this.uses = uses;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getUses() {
		return uses;
	}
	
	public void setUses(int uses) {
		this.uses = uses;
	}
}