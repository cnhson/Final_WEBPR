package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {

	public String getCurrentDateTime() {
		String datetime = "";
		try {
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

			datetime = currentDateTime.format(pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datetime;
	}


}
