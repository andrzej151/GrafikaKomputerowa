package Pracy;

import java.time.LocalTime;
import java.util.GregorianCalendar;

public class Praca {
	private LocalTime start, stop; 
	
	public void start() {
		start = LocalTime.now();
	}
	public void stop() {
		stop = LocalTime.now();
	}
	
	public String czaspracy() {
		return null;
		//GregorianCalendar g = new GregorianCalendar(System.currentTimeMillis());
	}

}
