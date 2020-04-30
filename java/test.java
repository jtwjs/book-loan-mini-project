import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class test {

	public static void main(String[] args) {
		String date = "19950307";
		Calendar time = new GregorianCalendar(Locale.KOREA);
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		int realYear = time.get(Calendar.YEAR);
		int realMonth = time.get(Calendar.MONTH);
		int realDay = time.get(Calendar.DATE);
		String yyyy = date.substring(0,4);
		String mm = date.substring(4,6);
		String dd = date.substring(6,8);
		int int_yyyy = Integer.parseInt(yyyy);
		int int_mm = Integer.parseInt(mm);
		int int_dd = Integer.parseInt(dd);
		cal.set(Calendar.YEAR, int_yyyy);
		cal.set(Calendar.MONTH,int_mm-1);
		cal.set(Calendar.DATE,int_dd);
		String time1 = fm.format(cal.getTime());
		System.out.println(time1);
	}

}
