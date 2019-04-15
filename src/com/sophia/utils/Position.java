package com.sophia.utils;

import java.io.Serializable;

public class Position implements Serializable{

	private static final long serialVersionUID = 2216335165206292027L;
	private String positionCode;
	private String positionName;
	private String description;

	@Column(name="POSITION_CODE",length=64)
	public String getPositionCode(){
		return positionCode;
	}
	public void setPositionCode(String positionCode){
		this.positionCode = positionCode;
	}
	@Column(name="POSITION_NAME",length=64)
	public String getPositionName(){
		return positionName;
	}
	public void setPositionName(String positionName){
		this.positionName = positionName;
	}
	@Column(name="DESCRIPTION",length=512)
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
}
