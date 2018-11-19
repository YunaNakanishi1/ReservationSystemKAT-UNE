package service;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import dao.ReservationDao;
import dto.ReservationDto;
import dto.Resource;

/**
 * サーブレット番号：6
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class GetReservationListFromResourceIdService {

	private ReservationDto resultList;
	private Resource _resource;
	private String _resourceId;
	private static Logger _log = LogManager.getLogger();

	@Override
	public void execute() throws SQLException {
		ReservationDao reservationDao = new ReservationDao();
		_resource = reservationDao.queryByResourceId(_resourceId);
		reservationDao.queryByResourceId(_resourceId){
			//queryByResourceId作成
			return resultList;
		}
	}

}
