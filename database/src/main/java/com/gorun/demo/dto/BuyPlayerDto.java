package com.gorun.demo.dto;

public class BuyPlayerDto {
	
	private int ourClubId;
	private int otherClubId;
	private int playerId;
	
	public BuyPlayerDto(int ourClubId, int otherClubId, int playerId) {
		super();
		this.ourClubId = ourClubId;
		this.otherClubId = otherClubId;
		this.playerId = playerId;
	}

	public int getOurClubId() {
		return ourClubId;
	}

	public void setOurClubId(int ourClubId) {
		this.ourClubId = ourClubId;
	}

	public int getOtherClubId() {
		return otherClubId;
	}

	public void setOtherClubId(int otherClubId) {
		this.otherClubId = otherClubId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "BuyPlayerDto [ourClubId=" + ourClubId + ", otherClubId=" + otherClubId + ", playerId=" + playerId + "]";
	}
	
	
	
	

}
