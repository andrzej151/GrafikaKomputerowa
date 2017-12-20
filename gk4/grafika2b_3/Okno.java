import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Okno extends JFrame {
    Image source;
    Image transform;
    ArrayList sourceWekt;
    ArrayList transformWekt;
    Przeksztalcenia transformacje;
    Ekran pierwszy;
    Ekran drugi;
    JRadioButton czyRastrowa;
    JRadioButton czyWektorowa;
    JFileChooser pliki;
    ButtonGroup wybor;
    ArrayList dowolnaPola;
    OkienkoMacierzy okienkoMacierzy;
    JCheckBox aproksymacja;
	
    public class OkienkoMacierzy extends JDialog {

        public void showOknoMacierzy() {
            for(int i = 0; i < 9; i++)
                if(i % 4 == 0)
                    ((JTextField)macierzPola.get(i)).setText("1");
                else
                    ((JTextField)macierzPola.get(i)).setText("0");

            macierz = (double[][])null;
            setVisible(true);
        }

        public MacierzPrzeksztalcen2D zwrF30107Macierz() {
            return macierz == null ? null : new MacierzPrzeksztalcen2D(macierz);
        }

        private ArrayList macierzPola;
        private double macierz[][];
        private JButton zloz;
        private JButton anuluj;

        OkienkoMacierzy(Frame parent) {
            super(parent, "Wpisz Macierz", true);
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            macierzPola = new ArrayList();
            for(int i = 0; i < 9; i++)
                if(i % 4 == 0)
                    macierzPola.add(new JTextField("1"));
                else
                    macierzPola.add(new JTextField("0"));

            c.ipadx = 40;
            c.ipady = 40;
            c.fill = 2;
            c.weightx = 100D;
            c.weighty = 100D;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.insets = new Insets(10, 10, 10, 10);
            c.gridx = 0;
            c.gridy = 0;
            add((Component)macierzPola.get(0), c);
            c.gridx = 1;
            c.gridy = 0;
            add((Component)macierzPola.get(1), c);
            c.gridx = 2;
            c.gridy = 0;
            add((Component)macierzPola.get(2), c);
            c.gridx = 0;
            c.gridy = 1;
            add((Component)macierzPola.get(3), c);
            c.gridx = 1;
            c.gridy = 1;
            add((Component)macierzPola.get(4), c);
            c.gridx = 2;
            c.gridy = 1;
            add((Component)macierzPola.get(5), c);
            c.gridx = 0;
            c.gridy = 2;
            add((Component)macierzPola.get(6), c);
            c.gridx = 1;
            c.gridy = 2;
            add((Component)macierzPola.get(7), c);
            c.gridx = 2;
            c.gridy = 2;
            add((Component)macierzPola.get(8), c);
            zloz = new JButton("Z\u0142\363\u017C");
            zloz.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    double mac[][] = new double[3][3];
                    for(int i = 0; i < 3; i++)
                        for(int j = 0; j < 3; j++)
                            try
                            {
                                mac[i][j] = Double.parseDouble(((JTextField)macierzPola.get(i * 3 + j)).getText());
                            }
                            catch(NumberFormatException ex)
                            {
                                JOptionPane.showMessageDialog(_fld0, "B\u0142\u0119dne dane");
                                return;
                            }

                    macierz = mac;
                    setVisible(false);
                }
            }
);
            anuluj = new JButton("Anuluj");
            anuluj.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            }
);
            c.fill = 0;
            c.ipady = 1;
            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 2;
            add(zloz, c);
            c.gridx = 3;
            add(anuluj, c);
            pack();
        }
    }

    class Ekran extends JPanel {

        void ustawRastrowa(Image buf) {
            im = buf;
            czyWektorowa = false;
            repaint();
        }

        void ustawWektorowa(ArrayList points) {
            punkty = points;
            czyWektorowa = true;
            repaint();
        }

        void wyczysc() {
            im = null;
            punkty = null;
            repaint();
        }

        public void paint(Graphics g) {
            super.paint(g);
			
            Graphics2D g2 = (Graphics2D)g;
            if(punkty == null && im == null) {
                Rectangle2D napis = g.getFontMetrics().getStringBounds("Brak Obrazka", g);
                g.drawString("Brak Obrazka", (int)((double)(getWidth() / 2) - napis.getWidth() / 2D), (int)((double)(getHeight() / 2) - napis.getHeight() / 2D));
            } 
			else if(czyWektorowa) {
                Punkt temp = (Punkt)punkty.get(0);
				
                for(Iterator i = punkty.iterator(); i.hasNext();) {
                    Punkt a = (Punkt)i.next();
                    g2.setStroke(pioro);
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.drawLine((int)temp.x, (int)temp.y, (int)a.x, (int)a.y);
                    temp = a;
                }

            } 
			else if(im.getHeight(Okno.this) > getHeight() && im.getWidth(Okno.this) > getWidth()) g.drawImage(im, 0, 0, null);
            else g.drawImage(im, (int)((double)getWidth() - dajWielkoscX()) / 2, (int)((double)getHeight() - dajWielkoscY()) / 2, null);
        }

        public void rysujNa(Graphics g) {
            g.drawImage(im, 0, 0, null);
        }

        boolean czyNamalowane() {
            return im == null || punkty == null;
        }

        double dajWielkoscX() {
            if(czyWektorowa) {
                double x = ((Punkt)punkty.get(1)).x;
                Iterator i = punkty.iterator();
                do {
                    if(!i.hasNext())
                        break;
                    Punkt a = (Punkt)i.next();
                    if(a.x > x)
                        x = a.x;
                } while(true);
                return x;
            }
			else {
                return (double)im.getWidth(Okno.this);
            }
        }

        double dajWielkoscY() {
            if(czyWektorowa) {
                double y = ((Punkt)punkty.get(1)).y;
                Iterator i = punkty.iterator();
                do
                {
                    if(!i.hasNext())
                        break;
                    Punkt a = (Punkt)i.next();
                    if(a.x > y)
                        y = a.y;
                } while(true);
                return y;
            } else
            {
                return (double)im.getHeight(Okno.this);
            }
        }

        private ArrayList punkty;
        private Image im;
        private boolean czyWektorowa;
        private BasicStroke pioro;
        final Okno this0;

        Ekran()
        {
            this0 = Okno.this;
            super();
            pioro = new BasicStroke(5F);
        }
    }


    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException ex)
        {
            Logger.getLogger(grafika2b/Okno.getName()).log(Level.SEVERE, null, ex);
        }
        catch(InstantiationException ex)
        {
            Logger.getLogger(grafika2b/Okno.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IllegalAccessException ex)
        {
            Logger.getLogger(grafika2b/Okno.getName()).log(Level.SEVERE, null, ex);
        }
        catch(UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(grafika2b/Okno.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                Okno okno = new Okno(Toolkit.getDefaultToolkit().getScreenSize());
            }

        }
);
    }

    Okno(Dimension desktop)
    {
        super("Grafika2b by Mati");
        okienkoMacierzy = new OkienkoMacierzy(this);
        ButtonGroup wyborSkalowania = new ButtonGroup();
        aproksymacja = new JCheckBox("Aproksymacja dwuliniowa");
        aproksymacja.setSelected(true);
        aproksymacja.setAlignmentX(0.5F);
        transformacje = new Przeksztalcenia();
        pliki = new JFileChooser();
        pliki.setCurrentDirectory(new File("D:\\"));
        setBounds(desktop.width / 4, desktop.height / 4, desktop.width / 2, desktop.height / 2 + 100);
        setDefaultCloseOperation(3);
        JPanel e1 = new JPanel(new BorderLayout());
        JPanel e2 = new JPanel(new BorderLayout());
        e1.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        e2.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        pierwszy = new Ekran();
        drugi = new Ekran();
        e1.add("Center", pierwszy);
        e2.add("Center", drugi);
        JPanel ekrany = new JPanel(new GridLayout(1, 2));
        ekrany.add(e1);
        ekrany.add(e2);
        add("Center", ekrany);
        JPanel sterowanie = new JPanel(new GridBagLayout());
        Box przyciski = Box.createVerticalBox();
        przyciski.setBorder(BorderFactory.createTitledBorder("Przyciski"));
        wybor = new ButtonGroup();
        czyRastrowa = new JRadioButton("Grafika Rastrowa");
        czyWektorowa = new JRadioButton("Grafika Wektorowa");
        wybor.add(czyRastrowa);
        wybor.add(czyWektorowa);
        czyRastrowa.setSelected(true);
        czyRastrowa.setAlignmentX(0.5F);
        czyWektorowa.setAlignmentX(0.5F);
        JButton wczytaj = new JButton("Wczytaj");
        wczytaj.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                pliki.showOpenDialog(pierwszy);
                if(pliki.getSelectedFile() == null)
                    return;
                File plik = pliki.getSelectedFile();
                if(czyRastrowa.isSelected())
                {
                    try
                    {
                        source = ImageIO.read(plik);
                        pierwszy.ustawRastrowa(source);
                        czyRastrowa.setEnabled(false);
                        czyWektorowa.setEnabled(false);
                    }
                    catch(IOException ex)
                    {
                        JOptionPane.showMessageDialog(Okno.this, (new StringBuilder()).append("B\u0142\u0105d IO ").append(ex).toString());
                    }
                } else
                {
                    Scanner wej = null;
                    try
                    {
                        wej = new Scanner(plik);
                        int liczbaPunktow = wej.nextInt();
                        sourceWekt = new ArrayList();
                        for(int i = 0; i < liczbaPunktow; i++)
                        {
                            double x = wej.nextInt();
                            double y = wej.nextInt();
                            sourceWekt.add(new Punkt(x, y));
                        }

                        pierwszy.ustawWektorowa(sourceWekt);
                        czyRastrowa.setEnabled(false);
                        czyWektorowa.setEnabled(false);
                    }
                    catch(FileNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(Okno.this, (new StringBuilder()).append("B\u0142\u0105d IO ").append(ex).toString());
                    }
                    wej.close();
                }
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        wczytaj.setAlignmentX(0.5F);
        JButton zapisz = new JButton("Zapisz");
        zapisz.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                pliki.showSaveDialog(drugi);
                if(pliki.getSelectedFile() == null)
                    return;
                File plik = pliki.getSelectedFile();
                if(drugi.czyNamalowane())
                    if(czyRastrowa.isSelected())
                    {
                        BufferedImage im = new BufferedImage((int)drugi.dajWielkoscX(), (int)drugi.dajWielkoscY(), 1);
                        drugi.rysujNa(im.getGraphics());
                        drugi.ustawRastrowa(im);
                        try
                        {
                            ImageIO.write(im, "PNG", plik);
                        }
                        catch(IOException ex)
                        {
                            JOptionPane.showMessageDialog(Okno.this, (new StringBuilder()).append("B\u0142\u0105d IO ").append(ex).toString());
                        }
                    } else
                    {
                        PrintWriter wyj = null;
                        try
                        {
                            wyj = new PrintWriter(plik);
                            wyj.println(transformWekt.size());
                            Punkt a;
                            for(Iterator i = transformWekt.iterator(); i.hasNext(); wyj.println((new StringBuilder()).append((int)a.x).append(" ").append((int)a.y).toString()))
                                a = (Punkt)i.next();

                        }
                        catch(FileNotFoundException ex)
                        {
                            JOptionPane.showMessageDialog(Okno.this, (new StringBuilder()).append("B\u0142\u0105d IO ").append(ex).toString());
                        }
                        wyj.close();
                    }
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        zapisz.setAlignmentX(0.5F);
        JButton wyczysc = new JButton("Wyczysc");
        wyczysc.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                source = null;
                sourceWekt = null;
                transform = null;
                transformWekt = null;
                pierwszy.wyczysc();
                drugi.wyczysc();
                czyRastrowa.setEnabled(true);
                czyWektorowa.setEnabled(true);
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        wyczysc.setAlignmentX(0.5F);
        JButton info = new JButton("Info");
        info.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(Okno.this, "Zrobione przez Matiego w 2011 roku :)");
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        info.setAlignmentX(0.5F);
        przyciski.add(czyWektorowa);
        przyciski.add(Box.createVerticalStrut(5));
        przyciski.add(czyRastrowa);
        przyciski.add(Box.createVerticalStrut(5));
        przyciski.add(aproksymacja);
        przyciski.add(Box.createVerticalStrut(5));
        przyciski.add(wczytaj);
        przyciski.add(Box.createVerticalStrut(5));
        przyciski.add(zapisz);
        przyciski.add(Box.createVerticalStrut(5));
        przyciski.add(wyczysc);
        przyciski.add(Box.createVerticalStrut(5));
        przyciski.add(info);
        przyciski.add(Box.createVerticalStrut(5));
        JTabbedPane zakladki = new JTabbedPane();
        JPanel przesun = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2;
        c.insets = new Insets(2, 2, 3, 3);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JLabel napis = new JLabel("o wektor");
        przesun.add(napis, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        napis = new JLabel("a ");
        przesun.add(napis, c);
        c.gridx = 1;
        c.gridy = 1;
        final JTextField przesun1 = new JTextField("0");
        przesun.add(przesun1, c);
        c.gridx = 0;
        c.gridy = 2;
        napis = new JLabel("b");
        przesun.add(napis, c);
        c.gridx = 1;
        c.gridy = 2;
        final JTextField przesun2 = new JTextField("0");
        przesun.add(przesun2, c);
        JButton przesunOK = new JButton("Wykonaj");
        przesunOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                if(czyWektorowa.isSelected())
                {
                    if(sourceWekt == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transformWekt = transformacje.przesun(sourceWekt, Integer.parseInt(przesun1.getText()), Integer.parseInt(przesun2.getText()));
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        przesun1.setText("0");
                        przesun2.setText("0");
                        return;
                    }
                    drugi.ustawWektorowa(transformWekt);
                } else
                {
                    if(source == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transform = transformacje.przesun((BufferedImage)source, Integer.parseInt(przesun1.getText()), Integer.parseInt(przesun2.getText()), aproksymacja.isSelected());
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        przesun1.setText("0");
                        przesun2.setText("0");
                        return;
                    }
                    drugi.ustawRastrowa(transform);
                }
            }

            final JTextField valprzesun1;
            final JTextField valprzesun2;
            final Okno this0;

            
            {
                this0 = Okno.this;
                przesun1 = jtextfield;
                przesun2 = jtextfield1;
                super();
            }
        }
);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        przesun.add(przesunOK, c);
        JPanel obroc = new JPanel(new GridBagLayout());
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        napis = new JLabel("K\u0105t");
        obroc.add(napis, c);
        c.gridx = 1;
        c.gridy = 0;
        final JTextField obroc1 = new JTextField("0");
        obroc.add(obroc1, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        napis = new JLabel("Wzgledem Pktu");
        obroc.add(napis, c);
        c.gridx = 0;
        c.gridy = 2;
        napis = new JLabel("X: ");
        obroc.add(napis, c);
        c.gridx = 1;
        c.gridy = 2;
        final JTextField obroc2 = new JTextField("0");
        obroc.add(obroc2, c);
        c.gridx = 0;
        c.gridy = 3;
        napis = new JLabel("Y: ");
        obroc.add(napis, c);
        c.gridx = 1;
        c.gridy = 3;
        final JTextField obroc3 = new JTextField("0");
        obroc.add(obroc3, c);
        c.gridx = 2;
        c.gridy = 0;
        final JCheckBox obrocSrodek = new JCheckBox("Wzgledem \u015Brodka");
        obroc.add(obrocSrodek, c);
        JButton obrocOK = new JButton("Wykonaj");
        obrocOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                double x;
                double y;
                if(obrocSrodek.isSelected())
                {
                    x = pierwszy.dajWielkoscX() / 2D;
                    y = pierwszy.dajWielkoscY() / 2D;
                } else
                {
                    x = Integer.parseInt(obroc2.getText());
                    y = Integer.parseInt(obroc3.getText());
                }
                if(czyWektorowa.isSelected())
                {
                    if(sourceWekt == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transformWekt = transformacje.obroc(sourceWekt, Double.parseDouble(obroc1.getText()), x, y);
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        obroc1.setText("0");
                        obroc2.setText("0");
                        obroc3.setText("0");
                        return;
                    }
                    drugi.ustawWektorowa(transformWekt);
                } else
                {
                    if(source == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transform = transformacje.obroc((BufferedImage)source, Double.parseDouble(obroc1.getText()), Integer.parseInt(obroc2.getText()), Integer.parseInt(obroc3.getText()), aproksymacja.isSelected());
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        obroc1.setText("0");
                        obroc2.setText("0");
                        obroc3.setText("0");
                        return;
                    }
                    drugi.ustawRastrowa(transform);
                }
            }

            final JCheckBox valobrocSrodek;
            final JTextField valobroc2;
            final JTextField valobroc3;
            final JTextField valobroc1;
            final Okno this0;

            
            {
                this0 = Okno.this;
                obrocSrodek = jcheckbox;
                obroc2 = jtextfield;
                obroc3 = jtextfield1;
                obroc1 = jtextfield2;
                super();
            }
        }
);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        obroc.add(obrocOK, c);
        JPanel skaluj = new JPanel(new GridBagLayout());
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        napis = new JLabel("a: ");
        skaluj.add(napis, c);
        c.gridx = 1;
        c.gridy = 0;
        final JTextField skaluj1 = new JTextField("1");
        skaluj.add(skaluj1, c);
        c.gridx = 0;
        c.gridy = 1;
        napis = new JLabel("d: ");
        skaluj.add(napis, c);
        c.gridx = 1;
        c.gridy = 1;
        final JTextField skaluj2 = new JTextField("1");
        skaluj.add(skaluj2, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        napis = new JLabel("Wzgledem Punku");
        skaluj.add(napis, c);
        c.gridx = 0;
        c.gridy = 3;
        napis = new JLabel("x: ");
        skaluj.add(napis, c);
        c.gridx = 1;
        c.gridy = 3;
        final JTextField skaluj3 = new JTextField("0");
        skaluj.add(skaluj3, c);
        c.gridx = 0;
        c.gridy = 4;
        napis = new JLabel("y: ");
        skaluj.add(napis, c);
        c.gridx = 1;
        c.gridy = 4;
        final JTextField skaluj4 = new JTextField("0");
        skaluj.add(skaluj4, c);
        c.gridx = 2;
        c.gridy = 1;
        final JRadioButton skalujPrzez = new JRadioButton("Przez Wsp\363\u0142czynnik");
        wyborSkalowania.add(skalujPrzez);
        skalujPrzez.setSelected(true);
        skaluj.add(skalujPrzez, c);
        c.gridx = 2;
        c.gridy = 2;
        JRadioButton skalujDo = new JRadioButton("Do rozdzielczosci");
        wyborSkalowania.add(skalujDo);
        skaluj.add(skalujDo, c);
        c.gridx = 2;
        c.gridy = 3;
        final JRadioButton skalujOkno = new JRadioButton("Do Wielkosci Okna");
        wyborSkalowania.add(skalujOkno);
        skaluj.add(skalujOkno, c);
        c.gridx = 2;
        c.gridy = 0;
        final JCheckBox skalujSrodek = new JCheckBox("Wzgledem \u015Brodka");
        skaluj.add(skalujSrodek, c);
        JButton skalujOK = new JButton("Wykonaj");
        skalujOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                double a = 1.0D;
                double d = 1.0D;
                double x = 0.0D;
                double y = 0.0D;
                if(skalujSrodek.isSelected())
                {
                    x = pierwszy.dajWielkoscX() / 2D;
                    y = pierwszy.dajWielkoscY() / 2D;
                } else
                {
                    try
                    {
                        x = Double.parseDouble(skaluj3.getText());
                        y = Double.parseDouble(skaluj4.getText());
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        skaluj1.setText("1");
                        skaluj2.setText("1");
                        skaluj3.setText("0");
                        skaluj4.setText("0");
                        return;
                    }
                }
                if(skalujPrzez.isSelected())
                    try
                    {
                        a = Double.parseDouble(skaluj1.getText());
                        d = Double.parseDouble(skaluj2.getText());
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        skaluj1.setText("1");
                        skaluj2.setText("1");
                        skaluj3.setText("0");
                        skaluj4.setText("0");
                        return;
                    }
                else
                if(skalujOkno.isSelected())
                {
                    a = (double)drugi.getWidth() / pierwszy.dajWielkoscX();
                    d = (double)drugi.getHeight() / pierwszy.dajWielkoscY();
                } else
                {
                    try
                    {
                        a = Double.parseDouble(skaluj1.getText()) / pierwszy.dajWielkoscX();
                        d = Double.parseDouble(skaluj2.getText()) / pierwszy.dajWielkoscY();
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        skaluj1.setText("1");
                        skaluj2.setText("1");
                        skaluj3.setText("0");
                        skaluj4.setText("0");
                        return;
                    }
                }
                if(czyWektorowa.isSelected())
                {
                    if(sourceWekt == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    transformWekt = transformacje.przeskaluj(sourceWekt, a, d, x, y);
                    drugi.ustawWektorowa(transformWekt);
                } else
                {
                    if(source == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    transform = transformacje.przeskaluj((BufferedImage)source, a, d, x, y, aproksymacja.isSelected());
                    drugi.ustawRastrowa(transform);
                }
            }

            final JCheckBox valskalujSrodek;
            final JTextField valskaluj3;
            final JTextField valskaluj4;
            final JTextField valskaluj1;
            final JTextField valskaluj2;
            final JRadioButton valskalujPrzez;
            final JRadioButton valskalujOkno;
            final Okno this0;

            
            {
                this0 = Okno.this;
                skalujSrodek = jcheckbox;
                skaluj3 = jtextfield;
                skaluj4 = jtextfield1;
                skaluj1 = jtextfield2;
                skaluj2 = jtextfield3;
                skalujPrzez = jradiobutton;
                skalujOkno = jradiobutton1;
                super();
            }
        }
);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        skaluj.add(skalujOK, c);
        JPanel odbij = new JPanel(new GridBagLayout());
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        napis = new JLabel("Prosta: ax+b");
        odbij.add(napis, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        napis = new JLabel("a: ");
        odbij.add(napis, c);
        c.gridx = 1;
        c.gridy = 1;
        final JTextField odbij1 = new JTextField("1");
        odbij.add(odbij1, c);
        c.gridx = 0;
        c.gridy = 2;
        napis = new JLabel("b: ");
        odbij.add(napis, c);
        c.gridx = 1;
        c.gridy = 2;
        final JTextField odbij2 = new JTextField("0");
        odbij.add(odbij2, c);
        JButton odbijOK = new JButton("Wykonaj");
        odbijOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                if(czyWektorowa.isSelected())
                {
                    if(sourceWekt == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transformWekt = transformacje.odbijProsta(sourceWekt, Double.parseDouble(odbij1.getText()), Double.parseDouble(odbij2.getText()));
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        odbij1.setText("1");
                        odbij2.setText("0");
                        return;
                    }
                    drugi.ustawWektorowa(transformWekt);
                } else
                {
                    if(source == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transform = transformacje.odbijProsta((BufferedImage)source, Double.parseDouble(odbij1.getText()), Double.parseDouble(odbij2.getText()), aproksymacja.isSelected());
                    }
                    catch(NumberFormatException numberFormatException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych");
                        odbij1.setText("1");
                        odbij2.setText("0");
                        return;
                    }
                    drugi.ustawRastrowa(transform);
                }
            }

            final JTextField valodbij1;
            final JTextField valodbij2;
            final Okno this0;

            
            {
                this0 = Okno.this;
                odbij1 = jtextfield;
                odbij2 = jtextfield1;
                super();
            }
        }
);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        odbij.add(odbijOK, c);
        JPanel dowolna = new JPanel(new GridBagLayout());
        dowolnaPola = new ArrayList();
        for(int i = 0; i < 9; i++)
        {
            if(i % 4 == 0)
                dowolnaPola.add(new JTextField("1"));
            else
                dowolnaPola.add(new JTextField("0"));
            ((JTextField)dowolnaPola.get(i)).setEditable(false);
            ((JTextField)dowolnaPola.get(i)).setPreferredSize(new Dimension(30, 30));
        }

        c.fill = 2;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 1.0D;
        napis = new JLabel("Wype\u0142nij macierz");
        dowolna.add(napis, c);
        c.ipadx = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        dowolna.add((Component)dowolnaPola.get(0), c);
        c.gridx = 1;
        c.gridy = 1;
        dowolna.add((Component)dowolnaPola.get(1), c);
        c.gridx = 2;
        c.gridy = 1;
        dowolna.add((Component)dowolnaPola.get(2), c);
        c.gridx = 0;
        c.gridy = 2;
        dowolna.add((Component)dowolnaPola.get(3), c);
        c.gridx = 1;
        c.gridy = 2;
        dowolna.add((Component)dowolnaPola.get(4), c);
        c.gridx = 2;
        c.gridy = 2;
        dowolna.add((Component)dowolnaPola.get(5), c);
        c.gridx = 0;
        c.gridy = 3;
        dowolna.add((Component)dowolnaPola.get(6), c);
        c.gridx = 1;
        c.gridy = 3;
        dowolna.add((Component)dowolnaPola.get(7), c);
        c.gridx = 2;
        c.gridy = 3;
        dowolna.add((Component)dowolnaPola.get(8), c);
        JButton dowolnaOK = new JButton("Wykonaj");
        dowolnaOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                double mac[][] = new double[3][3];
                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                        mac[i][j] = Double.parseDouble(((JTextField)dowolnaPola.get(i * 3 + j)).getText());

                }

                if(czyWektorowa.isSelected())
                {
                    if(sourceWekt == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    transformWekt = transformacje.dowolna(sourceWekt, mac);
                    if(transformWekt == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Obrazek wielkosci 0");
                        return;
                    }
                    drugi.ustawWektorowa(transformWekt);
                } else
                {
                    if(source == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Brak Obrazu");
                        return;
                    }
                    transform = transformacje.dowolna((BufferedImage)source, mac, aproksymacja.isSelected());
                    if(transform == null)
                    {
                        JOptionPane.showMessageDialog(pierwszy, "Obrazek wielkosci 0");
                        return;
                    }
                    drugi.ustawRastrowa(transform);
                }
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        c.fill = 0;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        dowolna.add(dowolnaOK, c);
        JButton zloz = new JButton("Zloz Macierz");
        zloz.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                double mac[][] = new double[3][3];
                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                        mac[i][j] = Double.parseDouble(((JTextField)dowolnaPola.get(i * 3 + j)).getText());

                }

                okienkoMacierzy.showOknoMacierzy();
                MacierzPrzeksztalcen2D macierz1 = new MacierzPrzeksztalcen2D(mac);
                MacierzPrzeksztalcen2D macierz2 = okienkoMacierzy.zwrF30107Macierz();
                if(macierz2 == null)
                    return;
                macierz1.pomnoz(macierz2);
                mac = macierz1.dajMacierz();
                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                        ((JTextField)dowolnaPola.get(i * 3 + j)).setText((new StringBuilder()).append(mac[i][j]).append("").toString());

                }

            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        c.gridx = 3;
        c.gridy = 4;
        c.gridwidth = 1;
        dowolna.add(zloz, c);
        JButton czyscDowolna = new JButton("Wyczysc");
        czyscDowolna.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                for(int i = 0; i < 9; i++)
                    if(i % 4 == 0)
                        ((JTextField)dowolnaPola.get(i)).setText("1");
                    else
                        ((JTextField)dowolnaPola.get(i)).setText("0");

            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        dowolna.add(czyscDowolna, c);
        JButton wczytajDowolna = new JButton("Wczytaj");
        wczytajDowolna.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                pliki.showOpenDialog(Okno.this);
                File plik = pliki.getSelectedFile();
                if(plik == null)
                    return;
                Scanner wej;
                try
                {
                    wej = new Scanner(plik);
                }
                catch(FileNotFoundException ex)
                {
                    JOptionPane.showMessageDialog(Okno.this, "Brak Pliku");
                    return;
                }
                for(int i = 0; i < 9; i++)
                {
                    if(!wej.hasNextInt())
                    {
                        wej.close();
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d danych w pliku");
                        if(i % 4 == 0)
                            ((JTextField)dowolnaPola.get(i)).setText("1");
                        else
                            ((JTextField)dowolnaPola.get(i)).setText("0");
                        return;
                    }
                    ((JTextField)dowolnaPola.get(i)).setText((new StringBuilder()).append("").append(wej.nextInt()).toString());
                }

                wej.close();
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        dowolna.add(wczytajDowolna, c);
        JButton zapiszDowolna = new JButton("Zapisz");
        zapiszDowolna.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                pliki.showSaveDialog(Okno.this);
                File plik = pliki.getSelectedFile();
                if(plik == null)
                    return;
                PrintWriter wyj;
                try
                {
                    wyj = new PrintWriter(plik);
                }
                catch(FileNotFoundException ex)
                {
                    JOptionPane.showMessageDialog(Okno.this, "Brak Pliku");
                    return;
                }
                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                        wyj.print((new StringBuilder()).append(((JTextField)dowolnaPola.get(j + i * 3)).getText()).append(" ").toString());

                    wyj.println();
                }

                wyj.close();
            }

            final Okno this0;

            
            {
                this0 = Okno.this;
                super();
            }
        }
);
        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 1;
        dowolna.add(zapiszDowolna, c);
        final JTextField dowolnaString = new JTextField();
        dowolnaString.setPreferredSize(new Dimension(100, 30));
        dowolnaString.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                if(czyWektorowa.isSelected())
                {
                    if(sourceWekt == null)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transformWekt = transformacje.liczZeStringa(sourceWekt, dowolnaString.getText());
                    }
                    catch(NoSuchElementException ex)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d Wprowadzonych danych");
                        return;
                    }
                    if(transformWekt == null)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "Obrazek wielkosci 0");
                        return;
                    }
                    drugi.ustawWektorowa(transformWekt);
                } else
                {
                    if(source == null)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "Brak Obrazu");
                        return;
                    }
                    try
                    {
                        transform = transformacje.liczZeStringa((BufferedImage)source, dowolnaString.getText(), aproksymacja.isSelected());
                    }
                    catch(NoSuchElementException noSuchElementException)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "B\u0142\u0105d Wprowadzonych danych");
                        return;
                    }
                    if(transform == null)
                    {
                        JOptionPane.showMessageDialog(Okno.this, "Obrazek wielkosci 0");
                        return;
                    }
                    drugi.ustawRastrowa(transform);
                }
            }

            final JTextField valdowolnaString;
            final Okno this0;

            
            {
                this0 = Okno.this;
                dowolnaString = jtextfield;
                super();
            }
        }
);
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        dowolna.add(dowolnaString, c);
        zakladki.addTab("Przesu\u0144", przesun);
        zakladki.addTab("Obr\363\u0107", obroc);
        zakladki.addTab("Skaluj", skaluj);
        zakladki.addTab("Odbij prost\u0105", odbij);
        zakladki.addTab("Dowolna Macierz", dowolna);
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 0.5D;
        sterowanie.add(przyciski, c);
        c.fill = 2;
        c.gridx = 3;
        c.gridwidth = 2;
        sterowanie.add(zakladki, c);
        add("South", sterowanie);
        show();
    }
}