import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class test {

	public static void main(String[] args) {
		Calendar time = new GregorianCalendar(Locale.KOREA);
		int realYear = time.get(Calendar.YEAR);
		int realMonth = time.get(Calendar.MONTH);
		int realDay = time.get(Calendar.DATE);
		System.out.println(realDay);

	}

}
