import java.io.PrintStream;

public class MacierzPrzeksztalcen2D {

    private double tab[][];

    MacierzPrzeksztalcen2D() {
        tab = new double[3][3];
    }

    MacierzPrzeksztalcen2D(double mac[][]) {
        if(mac.length == mac[0].length && mac.length == 3)
            tab = mac;
        else
            throw new NullPointerException();
    }

    void pomnoz(MacierzPrzeksztalcen2D macierz) {
        if(macierz.tab.length != tab[1].length || tab.length != 3)
            throw new RuntimeException();
        
	double mac[][] = new double[tab.length][macierz.tab[1].length];
        
	for(int i = 0; i < mac.length; i++)
            for(int j = 0; j < mac[0].length; j++)
                for(int x = 0; x < tab[1].length; x++)
                    mac[i][j] += tab[i][x] * macierz.tab[x][j];
        tab = mac;
    }

    static MacierzPrzeksztalcen2D macierzDiagonalna() {
        double mac[][] = new double[3][3];
        mac[0][0] = 1.0D;
        mac[1][1] = 1.0D;
        mac[2][2] = 1.0D;
        return new MacierzPrzeksztalcen2D(mac);
    }

    static MacierzPrzeksztalcen2D pomnozSkalarnie(MacierzPrzeksztalcen2D mac, double x) {
        MacierzPrzeksztalcen2D a = new MacierzPrzeksztalcen2D();
        a.tab = (double[][])mac.tab.clone();

        for(int i = 0; i < mac.tab[0].length; i++)
            for(int j = 0; j < mac.tab.length; j++)
                a.tab[i][j] *= x;

	return a;
    }

    static MacierzPrzeksztalcen2D macierzObrotu(double angle) {
        double mac[][] = new double[3][3];

        double sin = Math.abs(Math.sin(angle)) > 6.1232339957367662E-014D ? Math.sin(angle) : 0.0D;
        double cos = Math.abs(Math.cos(angle)) > 6.1232339957367662E-014D ? Math.cos(angle) : 0.0D;

        System.out.println((new StringBuilder()).append("Cosinus ").append(cos).append(" ").append(Math.abs(Math.cos(angle)) <= 6.1232339957367662E-014D).toString());
        
	mac[0][0] = cos;
        mac[0][1] = sin;
        mac[1][0] = -sin;
        mac[1][1] = cos;
        mac[2][2] = 1.0D;

        MacierzPrzeksztalcen2D a = new MacierzPrzeksztalcen2D(mac);
        return a;
    }

    static MacierzPrzeksztalcen2D macierzPrzesuniecia(double x, double y) {
        double mac[][] = new double[3][3];
        mac[0][0] = 1.0D;
        mac[1][1] = 1.0D;
        mac[2][0] = x;
        mac[2][1] = y;
        mac[2][2] = 1.0D;
        MacierzPrzeksztalcen2D a = new MacierzPrzeksztalcen2D(mac);
        return a;
    }

    static MacierzPrzeksztalcen2D macierzSkalowania(double a, double d) {
        double mac[][] = new double[3][3];
        mac[0][0] = a;
        mac[1][1] = d;
        mac[2][2] = 1.0D;
        MacierzPrzeksztalcen2D g = new MacierzPrzeksztalcen2D(mac);
        return g;
    }

    static MacierzPrzeksztalcen2D macierzDopelnienia(MacierzPrzeksztalcen2D a) {
        if(a.tab.length != a.tab[1].length || a.tab.length != 3)
        	throw new RuntimeException();
        else
        {
            double mac[][] = new double[3][3];
            mac[0][0] = a.tab[1][1] * a.tab[2][2] - a.tab[1][2] * a.tab[2][1];
            mac[0][1] = -(a.tab[1][0] * a.tab[2][2] - a.tab[1][2] * a.tab[2][0]);
            mac[0][2] = a.tab[1][0] * a.tab[2][1] - a.tab[1][1] * a.tab[2][0];
            mac[1][0] = -(a.tab[0][1] * a.tab[2][2] - a.tab[0][2] * a.tab[2][1]);
            mac[1][1] = a.tab[0][0] * a.tab[2][2] - a.tab[0][2] * a.tab[2][0];
            mac[1][2] = -(a.tab[0][0] * a.tab[2][1] - a.tab[0][1] * a.tab[2][0]);
            mac[2][0] = a.tab[0][1] * a.tab[1][2] - a.tab[0][2] * a.tab[1][1];
            mac[2][1] = -(a.tab[0][0] * a.tab[1][2] - a.tab[0][2] * a.tab[1][0]);
            mac[2][2] = a.tab[0][0] * a.tab[1][1] - a.tab[0][1] * a.tab[1][0];
            MacierzPrzeksztalcen2D x = new MacierzPrzeksztalcen2D(mac);
            return x;
        }
    }

    static MacierzPrzeksztalcen2D macierzTransponowana(MacierzPrzeksztalcen2D a) {
        double mac[][] = new double[a.tab[0].length][a.tab.length];

        for(int i = 0; i < mac[0].length; i++)
            for(int j = 0; j < mac.length; j++)
                mac[i][j] = a.tab[j][i];

        MacierzPrzeksztalcen2D x = new MacierzPrzeksztalcen2D(mac);
        x.tab = mac;
        return x;
    }

    static MacierzPrzeksztalcen2D macierzOdwrotna(MacierzPrzeksztalcen2D a) {
        return pomnozSkalarnie(macierzTransponowana(macierzDopelnienia(a)), 1.0D / wyznacznik(a));
    }

    static double wyznacznik(MacierzPrzeksztalcen2D a) {
        double tab[][] = a.tab;
        double w = (tab[0][0] * tab[1][1] * tab[2][2] + tab[0][1] * tab[1][2] * tab[2][0] + tab[0][2] * tab[1][0] * tab[2][1]) - tab[0][2] * tab[1][1] * tab[2][0] - tab[0][0] * tab[1][2] * tab[2][1] - tab[0][1] * tab[1][0] * tab[2][2];
        return w;
    }

    void przeksztalcPunkt(Punkt a) {
        double x = a.x * tab[0][0] + a.y * tab[1][0] + tab[2][0];
        double y = a.x * tab[0][1] + a.y * tab[1][1] + tab[2][1];
        double w = a.x * tab[0][2] + a.y * tab[1][2] + tab[2][2];

        if(w != 1.0D) {
            x /= w;
            y /= w;
        }

        a.x = x;
        a.y = y;
    }

    void drukujMacierz() {
        System.out.println("Macierz\n---------------");

        for(int i = 0; i < tab.length; i++) {
            for(int j = 0; j < tab[1].length; j++)
                System.out.print((new StringBuilder()).append(tab[i][j]).append(" ").toString());

            System.out.println();
        }

        System.out.println("---------------");
    }

    double[][] dajMacierz() {
        return (double[][])tab.clone();
    }
}