package com.reunion.enums;

public enum StatusDeRemboursement  {
	REMBOURSE("rembourse"), OUVERT("ouvert"), DATE_DEPASSEE("dateDepassee");
	private String value;

	StatusDeRemboursement(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
