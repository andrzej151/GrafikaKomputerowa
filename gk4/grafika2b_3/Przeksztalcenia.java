// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Przeksztalcenia.java

package grafika2b;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// Referenced classes of package grafika2b:
//            Punkt, MacierzPrzeksztalcen2D

public class Przeksztalcenia
{
    class Liczydlo extends RecursiveAction
    {

        protected void compute()
        {
            if(((wierszDo - wierszOd) + 1) * afterIm.getWidth() < treshhold || wektorowa)
            {
                computeDirectly();
                return;
            } else
            {
                int srodek = (wierszDo + wierszOd) / 2;
                invokeAll(new Liczydlo(im, afterIm, macierz, wierszOd, srodek, aproksymacja), new Liczydlo(im, afterIm, macierz, srodek, wierszDo, aproksymacja));
                computeDirectly();
                return;
            }
        }

        public void computeDirectly()
        {
            if(wektorowa)
            {
                for(int i = 0; i < punkty.size(); i++)
                    macierz.przeksztalcPunkt((Punkt)punkty.get(i));

            } else
            {
                Punkt a = new Punkt(0.0D, 0.0D);
                for(int i = wierszOd; i < wierszDo; i++)
                {
                    for(int j = 0; j < afterIm.getWidth(); j++)
                    {
                        a.x = j;
                        a.y = i;
                        macierz.przeksztalcPunkt(a);
                        Color color;
                        if(a.x >= (double)im.getWidth() || a.x < 0.0D || a.y < 0.0D || a.y >= (double)im.getHeight())
                            color = Color.BLACK;
                        else
                        if((a.x > (double)(int)a.x || a.y > (double)(int)a.y) && im.getHeight() > 1 && im.getWidth() > 1 && aproksymacja)
                            color = aproksymacjaDwuliniowa(im, a.x, a.y);
                        else
                            color = new Color(im.getRGB((int)a.x, (int)a.y));
                        afterIm.setRGB(j, i, color.getRGB());
                    }

                }

            }
        }

        private Color aproksymacjaDwuliniowa(BufferedImage im, double x, double y)
        {
            double alpha = x - (double)(int)x;
            double beta = y - (double)(int)y;
            Color A;
            Color B;
            Color C;
            Color D;
            if(x >= (double)(im.getWidth() - 1))
            {
                if(y >= (double)(im.getHeight() - 1))
                {
                    A = new Color(im.getRGB((int)x, (int)y));
                    B = new Color(im.getRGB((int)x - 1, (int)y));
                    C = new Color(im.getRGB((int)x, (int)y - 1));
                    D = new Color(im.getRGB((int)x - 1, (int)y - 1));
                } else
                {
                    A = new Color(im.getRGB((int)x, (int)y));
                    B = new Color(im.getRGB((int)x - 1, (int)y));
                    C = new Color(im.getRGB((int)x, (int)y + 1));
                    D = new Color(im.getRGB((int)x - 1, (int)y + 1));
                }
            } else
            if(y >= (double)(im.getHeight() - 1))
            {
                A = new Color(im.getRGB((int)x, (int)y));
                B = new Color(im.getRGB((int)x + 1, (int)y));
                C = new Color(im.getRGB((int)x, (int)y - 1));
                D = new Color(im.getRGB((int)x + 1, (int)y - 1));
            } else
            {
                A = new Color(im.getRGB((int)x, (int)y));
                B = new Color(im.getRGB((int)x + 1, (int)y));
                C = new Color(im.getRGB((int)x, (int)y + 1));
                D = new Color(im.getRGB((int)x + 1, (int)y + 1));
            }
            int blue = (int)((1.0D - beta) * ((1.0D - alpha) * (double)A.getBlue() + alpha * (double)B.getBlue()) + beta * ((1.0D - alpha) * (double)C.getBlue() + alpha * (double)D.getBlue()));
            int red = (int)((1.0D - beta) * ((1.0D - alpha) * (double)A.getRed() + alpha * (double)B.getRed()) + beta * ((1.0D - alpha) * (double)C.getRed() + alpha * (double)D.getRed()));
            int green = (int)((1.0D - beta) * ((1.0D - alpha) * (double)A.getGreen() + alpha * (double)B.getGreen()) + beta * ((1.0D - alpha) * (double)C.getGreen() + alpha * (double)D.getGreen()));
            Color color = new Color(red, green, blue);
            return color;
        }

        BufferedImage im;
        ArrayList punkty;
        BufferedImage afterIm;
        boolean wektorowa;
        MacierzPrzeksztalcen2D macierz;
        int treshhold;
        int wierszOd;
        int wierszDo;
        boolean aproksymacja;
        final Przeksztalcenia this$0;

        Liczydlo(Image source, BufferedImage transfered, MacierzPrzeksztalcen2D mac, int wierszOd, int wierszDo, boolean aproksymacja)
        {
            this$0 = Przeksztalcenia.this;
            super();
            treshhold = 0xf4240;
            im = (BufferedImage)source;
            afterIm = transfered;
            macierz = mac;
            wektorowa = false;
            this.wierszDo = wierszDo;
            this.wierszOd = wierszOd;
            this.aproksymacja = aproksymacja;
            System.out.println("fork/join");
        }

        Liczydlo(ArrayList punkty, MacierzPrzeksztalcen2D mac)
        {
            this$0 = Przeksztalcenia.this;
            super();
            treshhold = 0xf4240;
            this.punkty = punkty;
            macierz = mac;
            wektorowa = true;
        }
    }


    public Przeksztalcenia()
    {
    }

    Image obroc(BufferedImage im, double angle, double x, double y, 
            boolean aproksymacja)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y);
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu((angle * 3.1415926535897931D) / 180D));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
        macierz.drukujMacierz();
        Punkt Minmax[] = new Punkt[4];
        Minmax[0] = new Punkt(0.0D, 0.0D);
        Minmax[1] = new Punkt(im.getWidth() - 1, 0.0D);
        Minmax[2] = new Punkt(0.0D, im.getHeight() - 1);
        Minmax[3] = new Punkt(im.getWidth() - 1, im.getHeight() - 1);
        macierz.przeksztalcPunkt(Minmax[0]);
        macierz.przeksztalcPunkt(Minmax[1]);
        macierz.przeksztalcPunkt(Minmax[2]);
        macierz.przeksztalcPunkt(Minmax[3]);
        double maxx;
        double minx = maxx = Minmax[0].x;
        double maxy;
        double miny = maxy = Minmax[0].y;
        for(int i = 0; i < 4; i++)
        {
            if(Minmax[i].x > maxx)
                maxx = Minmax[i].x;
            if(Minmax[i].x < minx)
                minx = Minmax[i].x;
            if(Minmax[i].y > maxy)
                maxy = Minmax[i].y;
            if(Minmax[i].y < miny)
                miny = Minmax[i].y;
        }

        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-minx, -miny));
        Liczydlo licz = new Liczydlo(im, new BufferedImage((int)(maxx - minx), (int)(maxy - miny), 1), MacierzPrzeksztalcen2D.macierzOdwrotna(macierz), 0, (int)(maxy - miny), aproksymacja);
        ForkJoinPool pl = new ForkJoinPool();
        pl.invoke(licz);
        return licz.afterIm;
    }

    Image przeskaluj(BufferedImage im, double a, double d, double x, 
            double y, boolean aproksymacja)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y);
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzSkalowania(a, d));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
        macierz.drukujMacierz();
        Punkt Minmax[] = new Punkt[4];
        Minmax[0] = new Punkt(0.0D, 0.0D);
        Minmax[1] = new Punkt(im.getWidth() - 1, 0.0D);
        Minmax[2] = new Punkt(0.0D, im.getHeight() - 1);
        Minmax[3] = new Punkt(im.getWidth() - 1, im.getHeight() - 1);
        macierz.przeksztalcPunkt(Minmax[0]);
        macierz.przeksztalcPunkt(Minmax[1]);
        macierz.przeksztalcPunkt(Minmax[2]);
        macierz.przeksztalcPunkt(Minmax[3]);
        double maxx;
        double minx = maxx = Minmax[0].x;
        double maxy;
        double miny = maxy = Minmax[0].y;
        for(int i = 0; i < 4; i++)
        {
            if(Minmax[i].x > maxx)
                maxx = Minmax[i].x;
            if(Minmax[i].x < minx)
                minx = Minmax[i].x;
            if(Minmax[i].y > maxy)
                maxy = Minmax[i].y;
            if(Minmax[i].y < miny)
                miny = Minmax[i].y;
        }

        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-minx, -miny));
        Liczydlo licz = new Liczydlo(im, new BufferedImage((int)(maxx - minx), (int)(maxy - miny), 1), MacierzPrzeksztalcen2D.macierzOdwrotna(macierz), 0, (int)(maxy - miny), aproksymacja);
        ForkJoinPool pl = new ForkJoinPool();
        pl.invoke(licz);
        return licz.afterIm;
    }

    Image przesun(BufferedImage im, double x, double y, boolean aproksymacja)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y);
        macierz.drukujMacierz();
        System.out.println((new StringBuilder()).append(x).append(" ").append(y).toString());
        Liczydlo licz = new Liczydlo(im, new BufferedImage(im.getWidth(), im.getHeight(), 1), MacierzPrzeksztalcen2D.macierzOdwrotna(macierz), 0, im.getHeight(), aproksymacja);
        ForkJoinPool pl = new ForkJoinPool();
        pl.invoke(licz);
        return licz.afterIm;
    }

    Image odbijProsta(BufferedImage im, double a, double b, boolean aproksymacja)
    {
        double kat = Math.atan(a);
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzObrotu(6.2831853071795862D - kat);
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzSkalowania(1.0D, -1D));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu(kat));
        macierz.drukujMacierz();
        Punkt Minmax[] = new Punkt[4];
        Minmax[0] = new Punkt(0.0D, 0.0D);
        Minmax[1] = new Punkt(im.getWidth() - 1, 0.0D);
        Minmax[2] = new Punkt(0.0D, im.getHeight() - 1);
        Minmax[3] = new Punkt(im.getWidth() - 1, im.getHeight() - 1);
        macierz.przeksztalcPunkt(Minmax[0]);
        macierz.przeksztalcPunkt(Minmax[1]);
        macierz.przeksztalcPunkt(Minmax[2]);
        macierz.przeksztalcPunkt(Minmax[3]);
        double maxx;
        double minx = maxx = Minmax[0].x;
        double maxy;
        double miny = maxy = Minmax[0].y;
        for(int i = 0; i < 4; i++)
        {
            if(Minmax[i].x > maxx)
                maxx = Minmax[i].x;
            if(Minmax[i].x < minx)
                minx = Minmax[i].x;
            if(Minmax[i].y > maxy)
                maxy = Minmax[i].y;
            if(Minmax[i].y < miny)
                miny = Minmax[i].y;
        }

        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-minx, -miny));
        Liczydlo licz = new Liczydlo(im, new BufferedImage((int)(maxx - minx), (int)(maxy - miny), 1), MacierzPrzeksztalcen2D.macierzOdwrotna(macierz), 0, (int)(maxy - miny), aproksymacja);
        ForkJoinPool pl = new ForkJoinPool();
        pl.invoke(licz);
        return licz.afterIm;
    }

    Image dowolna(BufferedImage im, double mac[][], boolean aproksymacja)
    {
        MacierzPrzeksztalcen2D macierz = new MacierzPrzeksztalcen2D(mac);
        macierz.drukujMacierz();
        Punkt Minmax[] = new Punkt[4];
        Minmax[0] = new Punkt(0.0D, 0.0D);
        Minmax[1] = new Punkt(im.getWidth() - 1, 0.0D);
        Minmax[2] = new Punkt(0.0D, im.getHeight() - 1);
        Minmax[3] = new Punkt(im.getWidth() - 1, im.getHeight() - 1);
        macierz.przeksztalcPunkt(Minmax[0]);
        macierz.przeksztalcPunkt(Minmax[1]);
        macierz.przeksztalcPunkt(Minmax[2]);
        macierz.przeksztalcPunkt(Minmax[3]);
        double maxx = Minmax[0].x;
        double maxy = Minmax[0].y;
        for(int i = 1; i < 4; i++)
        {
            if(Minmax[i].x > maxx)
                maxx = Minmax[i].x;
            if(Minmax[i].y > maxy)
                maxy = Minmax[i].y;
        }

        Liczydlo licz;
        try
        {
            licz = new Liczydlo(im, new BufferedImage((int)maxx, (int)maxy, 1), MacierzPrzeksztalcen2D.macierzOdwrotna(macierz), 0, (int)maxy, aproksymacja);
        }
        catch(IllegalArgumentException ex)
        {
            return null;
        }
        ForkJoinPool pl = new ForkJoinPool();
        pl.invoke(licz);
        return licz.afterIm;
    }

    Image liczZeStringa(BufferedImage im, String s, boolean aproksymacja)
        throws NoSuchElementException
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzDiagonalna();
        Scanner wej;
        for(wej = new Scanner(s); wej.hasNext();)
        {
            String start = wej.next();
            if(start.equals("przesu\u0144"))
            {
                double x = wej.nextDouble();
                double y = wej.nextDouble();
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
            } else
            if(start.equals("obr\363\u0107"))
            {
                double kat = wej.nextDouble();
                double x = wej.hasNextDouble() ? wej.nextDouble() : im.getWidth() / 2;
                double y = wej.hasNextDouble() ? wej.nextDouble() : im.getHeight() / 2;
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu((kat * 3.1415926535897931D) / 180D));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
            } else
            if(start.equals("skaluj"))
            {
                double a = wej.nextDouble();
                double d = wej.nextDouble();
                double x = wej.hasNextDouble() ? wej.nextDouble() : im.getWidth() / 2;
                double y = wej.hasNextDouble() ? wej.nextDouble() : im.getHeight() / 2;
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzSkalowania(a, d));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
            } else
            {
                throw new NoSuchElementException();
            }
        }

        wej.close();
        return dowolna(im, macierz.dajMacierz(), aproksymacja);
    }

    ArrayList obroc(ArrayList punkty, double angle, double x, double y)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x - 1.0D, -y);
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu((angle / 180D) * 3.1415926535897931D));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
        macierz.drukujMacierz();
        ArrayList trans = new ArrayList();
        Punkt g;
        for(Iterator i$ = punkty.iterator(); i$.hasNext(); trans.add(new Punkt(g.x, g.y)))
            g = (Punkt)i$.next();

        Liczydlo licz = new Liczydlo(trans, macierz);
        licz.computeDirectly();
        return licz.punkty;
    }

    ArrayList przeskaluj(ArrayList punkty, double a, double d, double x, 
            double y)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y);
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzSkalowania(a, d));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
        macierz.drukujMacierz();
        ArrayList trans = new ArrayList();
        Punkt g;
        for(Iterator i$ = punkty.iterator(); i$.hasNext(); trans.add(new Punkt(g.x, g.y)))
            g = (Punkt)i$.next();

        Liczydlo licz = new Liczydlo(trans, macierz);
        licz.computeDirectly();
        return licz.punkty;
    }

    ArrayList przesun(ArrayList punkty, double x, double y)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y);
        macierz.drukujMacierz();
        ArrayList trans = new ArrayList();
        Punkt g;
        for(Iterator i$ = punkty.iterator(); i$.hasNext(); trans.add(new Punkt(g.x, g.y)))
            g = (Punkt)i$.next();

        Liczydlo licz = new Liczydlo(trans, macierz);
        licz.computeDirectly();
        return licz.punkty;
    }

    ArrayList odbijProsta(ArrayList punkty, double a, double b)
    {
        double kat = Math.atan(a);
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzPrzesuniecia(b, 0.0D);
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu(kat));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzSkalowania(1.0D, -1D));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu(-kat));
        macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-b, 0.0D));
        macierz.drukujMacierz();
        ArrayList trans = new ArrayList();
        Punkt g;
        for(Iterator i$ = punkty.iterator(); i$.hasNext(); trans.add(new Punkt(g.x, g.y)))
            g = (Punkt)i$.next();

        Liczydlo licz = new Liczydlo(trans, macierz);
        licz.computeDirectly();
        return licz.punkty;
    }

    ArrayList dowolna(ArrayList punkty, double mac[][])
    {
        MacierzPrzeksztalcen2D macierz = new MacierzPrzeksztalcen2D(mac);
        macierz.drukujMacierz();
        ArrayList trans = new ArrayList();
        Punkt g;
        for(Iterator i$ = punkty.iterator(); i$.hasNext(); trans.add(new Punkt(g.x, g.y)))
            g = (Punkt)i$.next();

        Liczydlo licz = new Liczydlo(trans, macierz);
        licz.computeDirectly();
        return licz.punkty;
    }

    ArrayList liczZeStringa(ArrayList punkty, String s)
    {
        MacierzPrzeksztalcen2D macierz = MacierzPrzeksztalcen2D.macierzDiagonalna();
        double width = ((Punkt)punkty.get(0)).y;
        double height = ((Punkt)punkty.get(0)).y;
        Iterator i$ = punkty.iterator();
        do
        {
            if(!i$.hasNext())
                break;
            Punkt a = (Punkt)i$.next();
            if(a.x > width)
                width = a.x;
            if(a.y > height)
                height = a.y;
        } while(true);
        Scanner wej;
        for(wej = new Scanner(s); wej.hasNext();)
            if(wej.next().equals("przesu\u0144"))
            {
                double x = wej.nextDouble();
                double y = wej.nextDouble();
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
            } else
            if(wej.next().equals("obr\363\u0107"))
            {
                double kat = wej.nextDouble();
                double x = wej.hasNextDouble() ? wej.nextDouble() : width / 2D;
                double y = wej.hasNextDouble() ? wej.nextDouble() : height / 2D;
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzObrotu((kat * 3.1415926535897931D) / 180D));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
            } else
            if(wej.next().equals("skaluj"))
            {
                double a = wej.nextDouble();
                double d = wej.nextDouble();
                double x = wej.hasNextDouble() ? wej.nextDouble() : width / 2D;
                double y = wej.hasNextDouble() ? wej.nextDouble() : height / 2D;
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(-x, -y));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzSkalowania(a, d));
                macierz.pomnoz(MacierzPrzeksztalcen2D.macierzPrzesuniecia(x, y));
            } else
            {
                throw new NoSuchElementException();
            }

        wej.close();
        return dowolna(punkty, macierz.dajMacierz());
    }
}
