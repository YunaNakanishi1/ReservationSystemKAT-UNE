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

import dto.FacilityDto;

/**
 * resource_characteristicsテーブルを扱うdaoクラス.
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class FacilityDao {

	private Connection _con=null;
	private static Logger _log = LogManager.getLogger();

	/**
     * リソース特性を全件取得するメソッド.
     *
     * @return 文字列のリスト(リソース特性テーブルが0件の場合、空のリストを返す）
     * @throws SQLException データベースに接続できない場合、SQLの実行に失敗した場合
     */
	public List<String> facility() throws SQLException {
		List<String> facilityList=new ArrayList<String>();
		DBHelper dbHelper =new DBHelper();
		_con=dbHelper.connectDb();

		String sql="select resource_characteristic_name from resource_characteristics order by resource_characteristic_id";

		Statement stmt=null;
		ResultSet rs=null;


			try{
				if (_con == null) {
					_log.error("DatabaseConnectError");
					throw new SQLException();
				}
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
					 _log.error("facility() Exception e1");
				}

				try {
					dbHelper.closeResource(rs);
				} catch (Exception e2) {
					e2.printStackTrace();
					_log.error("facility() Exception e2");
				}
				 dbHelper.closeDb();
			}



		return facilityList;
	}

	public List<FacilityDto> queryAll() throws SQLException {
		List<FacilityDto> facilityList = new ArrayList<FacilityDto>();
		DBHelper dbHelper=new DBHelper();
		_con=dbHelper.connectDb();

		if (_con == null) {
			_log.error("DatabaseConnectError");
			throw new SQLException();
		}
		Statement stmt=null;
		ResultSet rs=null;

		try{
			String sql="select _facilityId,_facilityName from resource_characteristics order by _facilityId";


			stmt=_con.createStatement();
			rs=stmt.executeQuery(sql);

			while(rs.next()){
				 facilityList.add(new FacilityDto(rs.getString(1), rs.getString(2)));
			}
		}finally{
			try{
				dbHelper.closeResource(stmt);

			}catch(Exception e1){
				e1.printStackTrace();
				 _log.error("FacilityName() Exception e1");
			}

			try {
				dbHelper.closeResource(rs);
			} catch (Exception e2) {
				e2.printStackTrace();
				_log.error("FacilityName() Exception e2");
			}
			 dbHelper.closeDb();
		}

		return  facilityList;
		}
	}

