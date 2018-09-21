package service;

import static handler.MessageHolder.*;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.UserDao;
import dto.User;

public class LogInService implements Service {

	private String _validationMessage;
	private User _inputUser;
	private User _resultUser;
	static final int ID_CHARACTER_NUMBER = 8;

	public LogInService(User user) {
		_inputUser = user;
	}

	@Override
	public boolean validate() {
		String userId = _inputUser.getUserId();
		String password = _inputUser.getPassword();

		//id半角チェック
		if (!checkHalfWidthChar(userId)) {
			_validationMessage = EM02;
			return false;
		}

		//id8文字チェック
		if (userId.length() != ID_CHARACTER_NUMBER) {
			_validationMessage = EM03;
			return false;
		}

		//password半角チェック
		if (!checkHalfWidthChar(password)) {
			_validationMessage = EM05;
			return false;
		}

		return true;
	}

	@Override
	public void execute() throws SQLException {
		UserDao ud = new UserDao();
		//認証されたユーザ情報
		_resultUser = ud.getUser(_inputUser);
		if (_resultUser == null) {
			_validationMessage = EM06;
		}
	}

	/**
	 * 文字列の半角全角チェック.
	 * @param line 入力された文字列
	 * @return 半角の場合true
	 */
	public boolean checkHalfWidthChar(String line) {
		Pattern p = Pattern.compile("^\\w*$");
		Matcher m = p.matcher(line);
		return m.find();
	}

	public User getResultUser() {
		return _resultUser;
	}

	public String getValidationMessage() {
		return _validationMessage;
	}

}
