/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import dto.ReservationDto;
import dto.Resource;

/**
 * (26).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class GetResouceFromIdService implements Service{

	 private ReservationDto _reserve;


	 public GetResouceFromIdService(String resourceId) {
		// TODO 自動生成されたコンストラクター・スタブ
	}




	@Override
	public boolean validate() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}


	@Override
	public void execute() throws SQLException {
		
	}

	public Resource getResource(){
		return null;

	}



}
