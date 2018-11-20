/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;
import java.util.List;

import dao.ResourceDao;
import dto.ReservationDto;


/**
*
* 権限削除処理を行うサービス.
* @author リコーITソリューションズ株式会社 KAT-UNE
*
*/
public class DeleteResourceService implements Service{

	private String _resourceId;    //削除したいリソースId
	private int  _result;          //削除結果
	private List<ReservationDto> _reservationList;

	public DeleteResourceService(String resourceId,List<ReservationDto> reservationList){
		_resourceId = resourceId;
		_reservationList=reservationList;
	}

	@Override
	public boolean validate(){
		return true;
	}

	public void execute() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		_result = resourceDao.delete(_resourceId,_reservationList);
	}

	public int getResult(){
		return _result;
	}

	//テスト用メソッド
	public String getResourceId(){
		return _resourceId;
	}
}
