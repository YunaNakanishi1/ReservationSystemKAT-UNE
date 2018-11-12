package service;

import java.sql.SQLException;
import java.util.List;

import dto.ReservationDto;
import dto.TimeDto;
import exception.MyException;

public class SearchReservationListService implements Service{

	private List<ReservationDto> _reservationList;

	private String _officeId;
	private String _categoryId;
	private String _usageDate;
	private TimeDto _usageStartTime;
	private TimeDto _usageEndTime;
	private String _userId;
	private boolean _onlyMyReservation;
	private boolean _pastReservation;
	private boolean _deletedReservation;




	public SearchReservationListService(String _officeId, String _categoryId, String _usageDate,
			TimeDto _usageStartTime, TimeDto _usageEndTime, String _userId, boolean _onlyMyReservation,
			boolean _pastReservation, boolean _deletedReservation) throws MyException{
		super();
		if(_userId==null||_usageStartTime==null||_usageEndTime==null){
			throw new MyException();
		}else if(_usageStartTime.getTimeMinutesValue()>_usageEndTime.getTimeMinutesValue()){
			throw new MyException();
		}

		this._officeId = _officeId;
		this._categoryId = _categoryId;
		this._usageDate = _usageDate;
		this._usageStartTime = _usageStartTime;
		this._usageEndTime = _usageEndTime;
		this._userId = _userId;
		this._onlyMyReservation = _onlyMyReservation;
		this._pastReservation = _pastReservation;
		this._deletedReservation = _deletedReservation;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() throws SQLException {
	}

	public List<ReservationDto> getReservationList() {
		return _reservationList;
	}




}
