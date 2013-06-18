package com.mike.wordrank.api.word;

import com.mike.wordrank.WordRankTypes.GroupType;

public class GroupWord extends Word {

	private String group;
	private GroupType type;
	
	public GroupWord(String name, String group, GroupType type, int uses) {
		super(name, uses);
		this.group = group;
		this.type = type;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public GroupType getType() {
		return type;
	}
	
	public void setType(GroupType type) {
		this.type = type;
	}
}