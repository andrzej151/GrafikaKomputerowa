package Pracy;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Czas {
	long start, koniec;
	GregorianCalendar startG, koniecG;

	public Czas() {
		start = -1;
		koniec = -1;
		startG = new GregorianCalendar();
		koniecG = new GregorianCalendar();
	}

	public String start() {
		if (start <= 0) {
			start = System.currentTimeMillis() / 1000;
			startG = new GregorianCalendar();
			return "Rozpoczeto prace";
		} else {
			return "juz rozpoczeto prace mozesz ja zakonczyc";
		}

	}

	public String koniec() {
		if (start <= 0) {
			return "rozpocznij prace";
		} else {

			koniec = System.currentTimeMillis() / 1000;
			koniecG = new GregorianCalendar();
			return "Zakonczono prace";
		}

	}

	public PrintWriter zapisz(PrintWriter out) {

		String s = "";
		System.out.print(out);
		if (start <= 0) {
			out.println("s0");
		} else {
			s = "s " + ((int) start) + " " + startG.get(Calendar.YEAR) + " " + startG.get(Calendar.MONTH) + " "
					+ startG.get(Calendar.DAY_OF_MONTH) + " " + startG.get(Calendar.HOUR_OF_DAY) + " "
					+ startG.get(Calendar.MINUTE);
			out.println(s);
		}
		if (koniec <= 0) {
			out.println("k0");
		} else {
			s="k " + ((int) koniec) + " " + koniecG.get(Calendar.YEAR) + " " + koniecG.get(Calendar.MONTH)
			+ " " + koniecG.get(Calendar.DAY_OF_MONTH) + " " + koniecG.get(Calendar.HOUR_OF_DAY) + " "
			+ koniecG.get(Calendar.MINUTE);
			
			out.println(s);
			int h = (int) ((koniec - start) / 3600);
			int m = (int) ((koniec - start) % 3600) / 60;
			int se = (int) ((koniec - start) % 60);
			out.println("c " + h + " " + m + " " + se);
		}
		return out;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s ="";
		if (start <= 0) {
			s+="s -";
		} else {
			s += startG.get(Calendar.YEAR) + "." + (startG.get(Calendar.MONTH)+1) + "."
					+ startG.get(Calendar.DAY_OF_MONTH) + " " + startG.get(Calendar.HOUR_OF_DAY) + ":"
					+ startG.get(Calendar.MINUTE)+" - ";
			
		}
		if (koniec <= 0) {
			s+="k0";
		} else {
			s+= koniecG.get(Calendar.YEAR) + "." + (koniecG.get(Calendar.MONTH)+1)
			+ "." + koniecG.get(Calendar.DAY_OF_MONTH) + " " + koniecG.get(Calendar.HOUR_OF_DAY) + ":"
			+ koniecG.get(Calendar.MINUTE)+" ";
			
			int h = (int) ((koniec - start) / 3600);
			int m = (int) ((koniec - start) % 3600) / 60;
			int se = (int) ((koniec - start) % 60);
			s+="czas " + h + ":" + m + ":" + se+"\n";
			
		
		}
		return s;
	}

	public Scanner wczytajCs(Scanner in) {
		// TODO Auto-generated method stub
		String s = in.nextLine();
		String[] w =s.split(" ");
		Integer inti = null;
		System.out.println("ok"+s);
		start = inti.parseInt(w[1]);
//		int y,m,d,h,mi;
//		in.next();
//		y = w[0]
//		in.next();
//		m = in.nextInt(0);
//		in.next();
//		d = in.nextInt(0);
//		in.next();
//		h = in.nextInt(0);
//		in.next();
//		mi = in.nextInt(0); 
		startG = new GregorianCalendar(inti.parseInt(w[2]), inti.parseInt(w[3]), inti.parseInt(w[4]), inti.parseInt(w[5]), inti.parseInt(w[6]));
		return in;
	}

	public Scanner wczytajCk(Scanner in) {
		// TODO Auto-generated method stub
		String s = in.nextLine();
		String[] w =s.split(" ");
		Integer inti = null;
		koniec = inti.parseInt(w[1]);
//		int y,m,d,h,mi;
//		in.next();
//		y = in.nextInt(0);
//		in.next();
//		m = in.nextInt(0);
//		in.next();
//		d = in.nextInt(0);
//		in.next();
//		h = in.nextInt(0);
//		in.next();
//		mi = in.nextInt(0);
		
		koniecG = new GregorianCalendar(inti.parseInt(w[2]), inti.parseInt(w[3]), inti.parseInt(w[4]), inti.parseInt(w[5]), inti.parseInt(w[6]));
		return in;
	}

	public long suma() {
		// TODO Auto-generated method stub
		return  koniec-start;
	}
}
