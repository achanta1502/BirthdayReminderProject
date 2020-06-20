package demo;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void test() throws ParseException {
		String str = "07-22";
		parseStringToDate(str);
	}
	
	private void parseStringToDate(String date) {
		String[] args = date.split("-");
		System.out.print(Arrays.toString(args));
		int year = LocalDateTime.now().getYear();
		LocalDateTime test = LocalDateTime.of(year, Integer.parseInt(args[0]), Integer.parseInt(args[1]), 0, 0);
		System.out.println(test.toString());
	}

}
