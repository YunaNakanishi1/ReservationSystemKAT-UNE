/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class DBHelper {

    private static Logger _log = LogManager.getLogger();
    private Connection _con = null;
    private static final String _url = "jdbc:postgresql://10.63.148.5:5432/KATUNEDB";
    private static final String _user = "ユーザ名";
    private static final String _password = "パスワード";

    /**
     * データベースへの接続をおこなう.
     * @return コネクションインスタンス
     */
    protected Connection connectDb() {

        // データベースに接続
        try {
            Class.forName("org.postgresql.Driver");
            _con = DriverManager.getConnection(_url, "postgres", "postgres");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            _log.error("connectDb() ClassNotFoundException");
        } catch (SQLException e) {
            e.printStackTrace();
            _log.error("connectDb() SQLException");
        }

        return _con;
    }

    /**
     * データベースとの接続を解除する.
     * 処理中の例外発生はログに記録
     */
    protected void closeDb() {

        if (_con != null) {
            try {
                _con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                _log.error("closeDb() SQLException");
            }
        }
    }

    /**
     * リソースをクローズする.
     * @param resource クローズ対象のリソース
     * @throws Exception SQLException以外の例外
     */
    protected void closeResource(AutoCloseable resource) throws Exception {

        if (resource != null) {
            try {
                resource.close();
            } catch (SQLException e) {
                e.printStackTrace();
                _log.error("closeResource() SQLException");
            }
        }
    }

}
