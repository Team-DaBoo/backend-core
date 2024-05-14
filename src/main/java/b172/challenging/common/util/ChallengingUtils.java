package b172.challenging.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChallengingUtils {
	// 날짜 형식의 String을 LocalDateTime으로 변환하는 메소드
	public static LocalDateTime parseStartStringToLocalDateTime(String dateString) {
		dateString = dateString.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		dateString = dateString.length() == 8 ? dateString += "000000" : dateString;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return LocalDateTime.parse(dateString, formatter);
	}

	public static LocalDateTime parseEndStringToLocalDateTime(String dateString) {
		dateString = dateString.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		dateString = dateString.length() == 8 ? dateString += "235959" : dateString;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return LocalDateTime.parse(dateString, formatter);
	}

	public static String parseLocalDateTimeToString(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter);
	}
}
