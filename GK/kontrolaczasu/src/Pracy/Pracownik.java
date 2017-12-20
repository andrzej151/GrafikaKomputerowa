package Pracy;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Pracownik {
	private String id;
	private ArrayList<Czas> praca;
	private Czas c;
	public Pracownik() {
		// TODO Auto-generated constructor stub
		id ="";
		praca = new ArrayList<>();
		
	}
	
	public String start()
	{
		if(c==null)
		{
			
			c = new Czas();
			praca.add(c);
			c.koniec();
			
			
			return c.start();
			
		}else {
			c.koniec();
			c = new Czas();
			praca.add(c);
			return c.start();
		}
		
	}
	
	public String koniec()
	{
		c = praca.get(praca.size()-1);
		return c.koniec();
		
	}
	
	public PrintWriter zapisz(PrintWriter out)
	{
		out.println("p "+ id );
		for(Czas c :praca) {
			out = c.zapisz(out);
		}
		out.println("kp");
		return out;
	}

	public boolean czypracownik(String nr) {
		// TODO Auto-generated method stub
		return id.equals(nr);
	}

	public void setid(String text) {
		// TODO Auto-generated method stub
		id = text;
	}

	public Scanner wczytaj(Scanner in) {
		// TODO Auto-generated method stub
		
		String s;
		Czas c = new Czas();
		praca = new ArrayList<>();
		s = in.next();
		setid(s);;
		while(in.hasNext()) {
			s= in.next();
			if(s.equals("s")) {
				c= new Czas();
				in = c.wczytajCs(in);
				praca.add(c);
			} else if(s.equals("k"))
			{
				in = c.wczytajCk(in);
			}else if(s.equals("k0")) {
				
			}else if(s.equals("kp")) {
				return in;
			}
		}
		
		return in;
	}
	
	

//	public String czaspracy() {
//		// TODO Auto-generated method stub
//		String s="";
//		int suma = 0;
//		for(Czas c :praca) {
//			suma = c.suma();
//			s+=c;
//		}
//		s= "Suma: "+ (suma / 3600)+":"+( (suma % 3600) / 60)+":"+(suma % 60)+"\n"+s;
//		
//		return s;
//	}

	public String czaspracysuma() {
		// TODO Auto-generated method stub
	
		long s = 0;
		for(Czas c :praca) {
			s += c.suma();
			
		}
		System.out.println(s);
		
	return "Suma: "+ (s / 3600)+":"+( (s % 3600) / 60)+":"+(s % 60);
		
	}

	public int size() {
		// TODO Auto-generated method stub
		return praca.size();
	}

	public String czas(int i) {
		// TODO Auto-generated method stub
		if(i<praca.size())
		{
		return praca.get(i).toString();
		}
		else {
			return "";
		}
	}

	
}
