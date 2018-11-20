package service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import dao.ReservationDao;
import dto.ReservationDto;
import exception.MyException;

public class GetReservationListBetweenDateService implements Service{
	private String _resourceId;
	private Timestamp _startTime;
	private Timestamp _endTime;
	private List<ReservationDto> _reservationList;


	public GetReservationListBetweenDateService(String resourceId, Timestamp startTime, Timestamp endTime) {
		super();

		if(resourceId==null||startTime==null||endTime==null){

			throw new MyException();
		}else if(startTime.after(endTime)){
			throw new MyException();
		}

		this._resourceId = resourceId;
		this._startTime = startTime;
		this._endTime = endTime;
	}

	@Override
	public boolean validate() {

		return true;
	}

	@Override
	public void execute() throws SQLException{

		ReservationDao reservationDao = new ReservationDao();
		//System.out.println(_resourceId + " " + _startTime + " " + _endTime);
		_reservationList=reservationDao.queryBetweenDate(_resourceId, _startTime, _endTime);
		//System.out.println("getReservationListBetween " + _reservationList.size());
	}

	public List<ReservationDto> getReservationList(){
		return _reservationList;
	}

}
