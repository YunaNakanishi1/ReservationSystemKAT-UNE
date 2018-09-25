/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class FacilityDao {

	private Connection _con=null;
	private static Logger _log = LogManager.getLogger();

	public List<String> facility() throws SQLException {
		List<String> facilityList=new ArrayList<String>();
		DBHelper dbHelper =new DBHelper();
		_con=dbHelper.connectDb();

		String sql="select resource_characteristic_name from resource_characteristics order by resource_characteristic_id";

		Statement stmt=null;
		ResultSet rs=null;

		if(_con !=null){
			try{
				stmt=_con.createStatement();
				rs=stmt.executeQuery(sql);

				while(rs.next()){
					facilityList.add(rs.getString(1));
				}
			}finally{
				try{
					dbHelper.closeResource(stmt);

				}catch(Exception e1){
					e1.printStackTrace();
					 _log.error("category() Exception e1");
				}

				try {
					dbHelper.closeResource(rs);
				} catch (Exception e2) {
					e2.printStackTrace();
					_log.error("category() Exception e2");
				}
				 dbHelper.closeDb();
			}

		}

		return facilityList;
	}
}
