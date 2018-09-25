package dao;

public class LineConverter {

	protected static String convertSingleMark(String line) {
		String returnLine = line.replace("'", "''");
		return returnLine;
	}
}
