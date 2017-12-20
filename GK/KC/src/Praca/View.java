package Praca;

public class View {

	private Model m;
	private FrameGlowne fg;
	private FrameAdmin fa;
	
	public View(Model m) {
		// TODO Auto-generated constructor stub
		this.m = m;
		fa = new FrameAdmin();
		fg = new FrameGlowne();
	}

}
