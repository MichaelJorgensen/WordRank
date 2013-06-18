package com.mike.wordrank;

public class WordRankTypes {

	public enum RedeemType {
		Chat, Command, Unknown;
		
		public static RedeemType getFrom(String x) {
			switch(x.toLowerCase()) {
			
			case "chat": return RedeemType.Chat;
			case "command": return RedeemType.Command;
			default: return RedeemType.Unknown;
			
			}
		}
	}
	
	public enum GroupType {
		Set, Add, Unknown;
		
		public static GroupType getFrom(String x) {
			switch(x.toLowerCase()) {
			
			case "set": return GroupType.Set;
			case "add": return GroupType.Add;
			default: return GroupType.Unknown;
			
			}
		}
	}
}