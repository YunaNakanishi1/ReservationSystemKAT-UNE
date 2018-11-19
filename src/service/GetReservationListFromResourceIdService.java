package service;

import java.sql.SQLException;
import java.util.List;

import dao.ReservationDao;
import dto.ReservationDto;
import exception.MyException;

/**
 * サーブレット番号：6
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class GetReservationListFromResourceIdService implements Service{

	private ReservationDto resultList;
	private String _resourceId;


	public GetReservationListFromResourceIdService(String resourceId){
		if(resourceId == null){
			throw new MyException();
		}
		this._resourceId = resourceId;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
		ReservationDao reservationDao = new ReservationDao();
		resultList = reservationDao.queryByResourceId(_resourceId);
	}

	public List<ReservationDto> getList(){
		return resultList;
	}


}