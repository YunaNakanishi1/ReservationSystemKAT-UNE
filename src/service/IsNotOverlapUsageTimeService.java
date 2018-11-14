package service;

import java.sql.Timestamp;

import dto.Resource;
import exception.MyException;

public class IsNotOverlapUsageTimeService implements Service {
	private Resource _resource;
	private Timestamp _startTime;
	private Timestamp _endTime;



	public IsNotOverlapUsageTimeService(Resource resource, Timestamp startTime, Timestamp endTime) {
		super();

		if(resource==null||startTime==null||endTime==null){
			throw new MyException();
		}else if(startTime.after(endTime)){
			throw new MyException();
		}

		this._resource = resource;
		this._startTime = startTime;
		this._endTime = endTime;
	}

	@Override
	public boolean validate() {
		Timestamp stopStartDate=_resource.getUsageStopStartDate();
		Timestamp stopEndDate=_resource.getUsageStopEndDate();

		if(stopStartDate!=null){
			if(_startTime.before(stopStartDate)){
				if(stopStartDate.before(_endTime)){
					return false;
				}
			}else{
				if(_startTime.before(stopEndDate)){
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void execute() {


	}

}
