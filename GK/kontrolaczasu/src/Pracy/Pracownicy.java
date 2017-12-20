package Pracy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Pracownicy implements ICzasPracy {

	private ArrayList<Pracownik> pracownicy;
	private String nazwabazy;

	public Pracownicy() {
		// TODO Auto-generated constructor stub
		nazwabazy = "Pracownicy.txt";
		pracownicy = new ArrayList<>();

	}

	@Override
	public String rozpoczeciepracy(String nr) {
		// TODO Auto-generated method stub
		for (Pracownik p : pracownicy) {
			if (p.czypracownik(nr)) {
			
				return p.start();
			}
		}

		return "nie znaleziono pracownika";
	}

	@Override
	public String zakonczeniepracy(String nr) {
		// TODO Auto-generated method stub
		for (Pracownik p : pracownicy) {
			if (p.czypracownik(nr)) {
				return p.koniec();
			}
		}

		return "nie znaleziono pracownika";
	}

	public String nowypracownik(String text) {
		// TODO Auto-generated method stub
		Pracownik pr = new Pracownik();
		pr.setid(text);
		pracownicy.add(pr);
		return "dodano nowego pracownika";
	}

	public boolean zapisz() {
		PrintWriter out;
		try {
			out = new PrintWriter(new File(nazwabazy));
			for (Pracownik p : pracownicy) {
				out = p.zapisz(out);
			}
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	
	public boolean wczytaj() {
		Scanner in;
		String s;
		Pracownik p;
		try {
			in = new Scanner(new File(nazwabazy));
			pracownicy = new ArrayList<>();
			while(in.hasNext()) {
				s = in.next();
				
				if(s.equals("p")) {
					p= new Pracownik();
					in = p.wczytaj(in);
					pracownicy.add(p);
					
				}
			}
			
			in.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block'
			
			try {
				PrintWriter out =new PrintWriter(new  File(nazwabazy));
				out.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return true;
		}
		
	}

	public Pracownik pracownik(String nr) {
		// TODO Auto-generated method stub
		
		for (Pracownik p : pracownicy) {
			if (p.czypracownik(nr)) {
				
				return p;
			}
		}
		return null;
	}

	public boolean reset() {
		// TODO Auto-generated method stub
		PrintWriter out;
		try {
			out = new PrintWriter(new File(nazwabazy));
			
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	

}
