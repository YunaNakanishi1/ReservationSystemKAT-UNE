package service;

import java.util.List;

import dto.ReservationDto;
import dto.Resource;
import dto.TimeDto;
import exception.MyException;

public class GetSliderWidthService implements Service {
	private List<ReservationDto> _reservationList;
	private ReservationDto _reservation;

	private TimeDto _startTimeSliderValue;
	private TimeDto _endTimeSliderValue;



	public GetSliderWidthService(List<ReservationDto> reservationList, ReservationDto reservation) {
		super();
		if(reservationList==null||reservation==null){
			throw new MyException();
		}

		this._reservationList = reservationList;
		this._reservation = reservation;
	}

	@Override
	public boolean validate() {

		return true;
	}

	@Override
	public void execute() {
		ServiceHelper serviceHelper = new ServiceHelper();

		String usageDate= _reservation.getUsageDate();
		Resource resource=_reservation.getResource();

		TimeDto usageStartTime=_reservation.getUsageStartTime();
		TimeDto sliderLeftValue=serviceHelper.getSliderLeftValue(usageStartTime, _reservationList);

		TimeDto usageStopStartValue=serviceHelper.convertUsageStopTimeToOneDay(resource.getUsageStopStartDate(), usageDate);
		if(sliderLeftValue.getTimeMinutesValue()<usageStopStartValue.getTimeMinutesValue()){
			_startTimeSliderValue=usageStartTime;
		}else{
			_startTimeSliderValue=sliderLeftValue;
		}

		TimeDto usageEndTime=_reservation.getUsageEndTime();

		//System.out.println(usageEndTime.getHour() + "です");
		//System.out.println(_reservationList.size());

		TimeDto sliderRightValue=serviceHelper.getSliderRightValue(usageEndTime, _reservationList);

		TimeDto usageStopEndValue=serviceHelper.convertUsageStopTimeToOneDay(resource.getUsageStopEndDate(), usageDate);

//		System.out.println(sliderRightValue.getTimeMinutesValue());
//		System.out.println(sliderRightValue.getTimeMinutesValue());

		if(sliderRightValue.getTimeMinutesValue()<usageStopEndValue.getTimeMinutesValue()){
			_endTimeSliderValue= usageStopEndValue;
		}else{
			_endTimeSliderValue= sliderRightValue;
		}


	}

	public TimeDto getStartTimeSliderValue() {
		return _startTimeSliderValue;
	}

	public TimeDto getEndTimeSliderValue() {
		return _endTimeSliderValue;
	}



}
