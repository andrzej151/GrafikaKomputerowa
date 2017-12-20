package gkb2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Swing2 {
	
	public static JFrame mainApplicationFrame;
	private JPanel panel;
	private JPanel otherPanel;
	private JTextField filePathTextField;
	private JRadioButton rdbtnGrafikaRastrowa, rdbtnGrafikaWektorowa;
	private JButton readButton, drawButton;
	
	private MyPanel firstPanel, secondPanel;
	private ArrayList<Line> sourceWekt, transformWekt;
	private Image source, transform;
	private Transformations transformation;
	
	/** Launch the application. */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new Swing2();
					Swing2.mainApplicationFrame.setVisible(true);
				}
				
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/** Create the application. */
	public Swing2() {
		transformation = new Transformations();
		
		Dimension center = (Toolkit.getDefaultToolkit()).getScreenSize();
		
		Swing2.mainApplicationFrame = new JFrame();
		Swing2.mainApplicationFrame.setBounds((int) (center.width * 0.2), (int) (center.height * 0.2), 800, 555);
		Swing2.mainApplicationFrame.setTitle("Grafika komputerowa - Zadanie 2");
		Swing2.mainApplicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fillFrame();
	}
	
	/** Filling main application frame with components. */
	private void fillFrame() {
		/* Defining application layout. */
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
		gridBagLayout.columnWidths = new int[] { 390, 390 };
		gridBagLayout.rowHeights = new int[] { 300, 200 };
		Swing2.mainApplicationFrame.getContentPane().setLayout(gridBagLayout);
		
		/* Defining  panel. */
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Obraz ", TitledBorder.LEADING, TitledBorder.TOP, null));
		panel.setLayout(new BorderLayout());
		
		firstPanel = new MyPanel(Swing2.mainApplicationFrame);
		panel.add(firstPanel);
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		Swing2.mainApplicationFrame.getContentPane().add(panel, gbc_panel);
		
		/* Defining other panel. */
		otherPanel = new JPanel();
		otherPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Obraz po konwersji", TitledBorder.LEADING, TitledBorder.TOP, null));
		otherPanel.setLayout(new BorderLayout());
		
		secondPanel = new MyPanel(Swing2.mainApplicationFrame);
		otherPanel.add(secondPanel);
		
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.insets = new Insets(0, 0, 5, 0);
		gbc_panel2.fill = GridBagConstraints.BOTH;
		gbc_panel2.gridx = 1;
		gbc_panel2.gridy = 0;
		Swing2.mainApplicationFrame.getContentPane().add(otherPanel, gbc_panel2);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Obs³uga", TitledBorder.LEADING, TitledBorder.TOP, null));
		
		filePathTextField = new JTextField();
		filePathTextField.setBounds(17, 55, 257, 20);
		
		JLabel filePathLabel = new JLabel("Œcie¿ka do pliku:");
		filePathLabel.setBounds(17, 30, 95, 16);
		
		JButton browseJButton = new JButton("Przegl¹daj");
		browseJButton.setBounds(279, 52, 95, 26);
		browseJButton.addActionListener(new FileBrowserActionListener());
		
		drawButton = new JButton("Rysuj");
		drawButton.setEnabled(false);
		drawButton.setBounds(106, 112, 79, 26);
		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					sourceWekt = getPoints(filePathTextField.getText());
					transformWekt = getPoints(filePathTextField.getText());
					
					firstPanel.ustawWektorowa(sourceWekt);
					secondPanel.ustawWektorowa(transformWekt);
					
					rdbtnGrafikaWektorowa.setEnabled(false);
					rdbtnGrafikaRastrowa.setEnabled(false);
					drawButton.setEnabled(false);
				}
				catch (Exception e) {
					
					firstPanel.wyczysc();
					secondPanel.wyczysc();
					JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B³¹d wczytywania pliku!");
				}
			}
		});
		
		readButton = new JButton("Wczytaj");
		readButton.setBounds(17, 112, 79, 26);
		readButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					source = ImageIO.read(new File(filePathTextField.getText()));
					transform = ImageIO.read(new File(filePathTextField.getText()));
					
					firstPanel.ustawRastrowa(source);
					secondPanel.ustawRastrowa(transform);
					
					rdbtnGrafikaWektorowa.setEnabled(false);
					rdbtnGrafikaRastrowa.setEnabled(false);
					readButton.setEnabled(false);
				}
				catch (IOException e) {
					firstPanel.wyczysc();
					secondPanel.wyczysc();
					JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B³¹d wczytywania pliku!");
				}
			}
		});
		
		JButton clearJButton = new JButton("Czyœæ");
		clearJButton.setBounds(17, 182, 168, 26);
		clearJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				firstPanel.wyczysc();
				secondPanel.wyczysc();
				
				sourceWekt = null;
				transformWekt = null;
				source = null;
				transform = null;
				
				if (!readButton.isEnabled() && !drawButton.isEnabled()) {
					rdbtnGrafikaWektorowa.setEnabled(true);
					rdbtnGrafikaWektorowa.setSelected(false);
					rdbtnGrafikaRastrowa.setEnabled(true);
					rdbtnGrafikaRastrowa.setSelected(true);
					readButton.setEnabled(true);
				}
			}
		});
		
		menuPanel.add(filePathTextField);
		menuPanel.add(browseJButton);
		menuPanel.add(filePathLabel);
		menuPanel.add(readButton);
		menuPanel.add(clearJButton);
		menuPanel.add(drawButton);
		
		menuPanel.setLayout(null);
		GridBagConstraints gbc_menuPanel = new GridBagConstraints();
		gbc_menuPanel.insets = new Insets(0, 0, 0, 5);
		gbc_menuPanel.fill = GridBagConstraints.BOTH;
		gbc_menuPanel.gridx = 0;
		gbc_menuPanel.gridy = 1;
		Swing2.mainApplicationFrame.getContentPane().add(menuPanel, gbc_menuPanel);
		
		rdbtnGrafikaWektorowa = new JRadioButton("Grafika wektorowa");
		rdbtnGrafikaWektorowa.setBounds(128, 82, 115, 23);
		rdbtnGrafikaWektorowa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGrafikaWektorowa.isSelected()) {
					rdbtnGrafikaRastrowa.setSelected(false);
					readButton.setEnabled(false);
					drawButton.setEnabled(true);
				}
				else rdbtnGrafikaWektorowa.setSelected(true);
			}
		});
		menuPanel.add(rdbtnGrafikaWektorowa);
		
		rdbtnGrafikaRastrowa = new JRadioButton("Grafika rastrowa");
		rdbtnGrafikaRastrowa.setBounds(17, 82, 109, 23);
		rdbtnGrafikaRastrowa.setSelected(true);
		rdbtnGrafikaRastrowa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGrafikaRastrowa.isSelected()) {
					rdbtnGrafikaWektorowa.setSelected(false);
					readButton.setEnabled(true);
					drawButton.setEnabled(false);
				}
				else rdbtnGrafikaRastrowa.setSelected(true);
			}
		});
		menuPanel.add(rdbtnGrafikaRastrowa);
		
		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.setBounds(17, 145, 168, 26);
		btnZapisz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String path = filePathTextField.getText();
				String extension = path.substring(path.length() - 3).toUpperCase();
				
				if (rdbtnGrafikaRastrowa.isSelected() && transform != null) {
					try {
						ImageIO.write((RenderedImage) transform, extension, new File(path));
					}
					catch (IOException e) {
						JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych w pliku!");
					}
				}
				else if (rdbtnGrafikaWektorowa.isSelected() && transformWekt != null && !transformWekt.isEmpty()) {
					try(PrintWriter fw = new PrintWriter(new File(path))) {
						fw.println(transformWekt.size());
						
						for(Line l: transformWekt)
							fw.println(l.toString());
					}
					catch (IOException e) {
						JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych w pliku!");
					}
				}
			}
		});
		
		menuPanel.add(btnZapisz);
		
		JButton btnWykonaj = new JButton("Wykonaj");
		btnWykonaj.setBounds(206, 112, 168, 96);
		btnWykonaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnGrafikaWektorowa.isSelected()) secondPanel.ustawWektorowa(transformWekt);
				else secondPanel.ustawRastrowa(transform);
			}
		});
		menuPanel.add(btnWykonaj);
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		
		Move move = new Move();
		tabbedPane.addTab("Przesuwanie", move);
		
		Rotate rotate = new Rotate();
		tabbedPane.addTab("Obracanie", rotate);
		
		Scale scale = new Scale();
		tabbedPane.addTab("Skalowanie", scale);
		
		AnyMatrix anyMatrix = new AnyMatrix();
		tabbedPane.addTab("Swobodne", anyMatrix);
		
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 1;
		Swing2.mainApplicationFrame.getContentPane().add(tabbedPane, gbc_tabbedPane);
	}
	
	private ArrayList<Line> getPoints(String plik) throws Exception {
		try(Scanner wej = new Scanner(new File(plik))) {
			int liczbaPunktow = wej.nextInt();
			
			ArrayList<Line> sourceWekt = new ArrayList<Line>();
			
			for(int i = 0; i < liczbaPunktow; i++)
				sourceWekt.add(new Line(new Point(wej.nextInt(), wej.nextInt()), new Point(wej.nextInt(), wej.nextInt())));
			
			return sourceWekt;
		}
	}
	
	/** Browsing for file. */
	class FileBrowserActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser c = new JFileChooser("C:\\");
			int selectedFile = c.showDialog(Swing2.mainApplicationFrame, "Wybierz");
			if (selectedFile == JFileChooser.APPROVE_OPTION) filePathTextField.setText(c.getSelectedFile().getAbsolutePath());
		}
	}
	
	class Move extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private JTextField e, f;
		
		public Move() {
			
			setBackground(SystemColor.activeCaption);
			setLayout(null);
			
			JLabel label = new JLabel("Wsp\u00F3\u0142rz\u0119dne wektora:");
			label.setBounds(10, 10, 110, 20);
			add(label);
			
			JTextField textField = new JTextField();
			textField.setBackground(SystemColor.controlHighlight);
			textField.setEnabled(false);
			textField.setText(" 1");
			textField.setBounds(40, 40, 30, 20);
			add(textField);
			
			JTextField textField_6 = new JTextField();
			textField_6.setBackground(SystemColor.controlHighlight);
			textField_6.setEnabled(false);
			textField_6.setText(" 0");
			textField_6.setBounds(90, 40, 30, 20);
			add(textField_6);
			
			JTextField textField_3 = new JTextField();
			textField_3.setBackground(SystemColor.controlHighlight);
			textField_3.setText(" 0");
			textField_3.setEnabled(false);
			textField_3.setBounds(140, 40, 30, 20);
			add(textField_3);
			
			JTextField textField_7 = new JTextField();
			textField_7.setBackground(SystemColor.controlHighlight);
			textField_7.setText(" 0");
			textField_7.setEnabled(false);
			textField_7.setBounds(40, 80, 30, 20);
			add(textField_7);
			
			JTextField textField_4 = new JTextField();
			textField_4.setBackground(SystemColor.controlHighlight);
			textField_4.setText(" 1");
			textField_4.setEnabled(false);
			textField_4.setBounds(90, 80, 30, 20);
			add(textField_4);
			
			JTextField textField_8 = new JTextField();
			textField_8.setBackground(SystemColor.controlHighlight);
			textField_8.setText(" 0");
			textField_8.setEnabled(false);
			textField_8.setBounds(140, 80, 30, 20);
			add(textField_8);
			
			e = new JTextField();
			e.setBounds(40, 120, 30, 20);
			add(e);
			
			f = new JTextField();
			f.setBounds(90, 120, 30, 20);
			add(f);
			
			JTextField textField_9 = new JTextField();
			textField_9.setBackground(SystemColor.controlHighlight);
			textField_9.setText(" 1");
			textField_9.setEnabled(false);
			textField_9.setBounds(140, 120, 30, 20);
			add(textField_9);
			
			JLabel lblE = new JLabel("e:");
			lblE.setBounds(28, 125, 10, 14);
			add(lblE);
			
			JLabel lblF = new JLabel("f:");
			lblF.setBounds(80, 125, 10, 14);
			add(lblF);
			
			JButton moveButton = new JButton("Przesu\u0144");
			moveButton.setBounds(40, 151, 130, 23);
			moveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					if (rdbtnGrafikaWektorowa.isSelected()) {
						if (sourceWekt == null) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
							return;
						}
						try {
							transformWekt = transformation.przesun(transformWekt, Integer.parseInt(e.getText()), Integer.parseInt(f.getText()));
						}
						catch (NumberFormatException numberFormatException) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych!");
							e.setText("0");
							f.setText("0");
							return;
						}
						// TODO secondPanel.ustawWektorowa(transformWekt);
					}
					else {
						if (source == null) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
							return;
						}
						try {
							transform = transformation.przesun((BufferedImage) transform, Integer.parseInt(e.getText()), Integer.parseInt(f.getText()));
						}
						catch (NumberFormatException numberFormatException) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych!");
							e.setText("0");
							f.setText("0");
							return;
						}
						// TODO secondPanel.ustawRastrowa(transform);
					}
				}
			});
			add(moveButton);
		}
	}
	
	class Scale extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField a, d;
		
		public Scale() {
			
			setBackground(SystemColor.activeCaption);
			setLayout(null);
			
			JLabel label = new JLabel("Wartoœci skali:");
			label.setBounds(10, 10, 110, 20);
			add(label);
			
			a = new JTextField();
			a.setBounds(40, 40, 30, 20);
			add(a);
			
			JTextField textField_6 = new JTextField();
			textField_6.setBackground(SystemColor.controlHighlight);
			textField_6.setEnabled(false);
			textField_6.setText(" 0");
			textField_6.setBounds(85, 41, 30, 20);
			add(textField_6);
			
			JTextField textField_3 = new JTextField();
			textField_3.setBackground(SystemColor.controlHighlight);
			textField_3.setText(" 0");
			textField_3.setEnabled(false);
			textField_3.setBounds(128, 40, 30, 20);
			add(textField_3);
			
			JTextField textField_7 = new JTextField();
			textField_7.setBackground(SystemColor.controlHighlight);
			textField_7.setText(" 0");
			textField_7.setEnabled(false);
			textField_7.setBounds(40, 80, 30, 20);
			add(textField_7);
			
			d = new JTextField();
			d.setBounds(85, 81, 30, 20);
			add(d);
			
			JTextField textField_8 = new JTextField();
			textField_8.setBackground(SystemColor.controlHighlight);
			textField_8.setText(" 0");
			textField_8.setEnabled(false);
			textField_8.setBounds(128, 80, 30, 20);
			add(textField_8);
			
			JTextField textField_5 = new JTextField();
			textField_5.setBackground(SystemColor.controlHighlight);
			textField_5.setText(" 0");
			textField_5.setEnabled(false);
			textField_5.setBounds(40, 120, 30, 20);
			add(textField_5);
			
			JTextField textField_2 = new JTextField();
			textField_2.setBackground(SystemColor.controlHighlight);
			textField_2.setText(" 0");
			textField_2.setEnabled(false);
			textField_2.setBounds(85, 121, 30, 20);
			add(textField_2);
			
			JTextField textField_9 = new JTextField();
			textField_9.setBackground(SystemColor.controlHighlight);
			textField_9.setText(" 1");
			textField_9.setEnabled(false);
			textField_9.setBounds(128, 120, 30, 20);
			add(textField_9);
			
			JLabel aLabel = new JLabel("a:");
			aLabel.setBounds(28, 45, 10, 14);
			add(aLabel);
			
			JLabel dLabel = new JLabel("d:");
			dLabel.setBounds(73, 86, 10, 14);
			add(dLabel);
			
			JButton moveButton = new JButton("Skaluj");
			moveButton.setBounds(40, 151, 118, 23);
			moveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					double aVal, dVal;
					try {
						aVal = Double.parseDouble(a.getText());
						dVal = Double.parseDouble(d.getText());
					}
					catch (Exception ex) {
						JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych!");
						a.setText("0");
						d.setText("0");
						return;
					}
					
					if (rdbtnGrafikaWektorowa.isSelected()) {
						if (transformWekt == null) JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
						else {
							transformWekt = transformation.przeskaluj(transformWekt, aVal, dVal, 0D, 0D);
							// TODO secondPanel.ustawWektorowa(transformWekt);
						}
					}
					else {
						if (transform == null) JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
						else {
							transform = transformation.przeskaluj((BufferedImage) transform, aVal, dVal, (int) (transform.getWidth(secondPanel) * aVal), (int) (transform.getHeight(secondPanel) * dVal));
							// TODO secondPanel.ustawRastrowa(transform);
						}
					}
				}
			});
			add(moveButton);
			
			JLabel lblX = new JLabel("x:");
			lblX.setBounds(211, 43, 20, 14);
			add(lblX);
			
			JLabel lblY = new JLabel("y:");
			lblY.setBounds(211, 83, 20, 14);
			add(lblY);
			
			final JTextField textField_1 = new JTextField();
			textField_1.setBounds(232, 40, 86, 20);
			add(textField_1);
			textField_1.setColumns(10);
			
			final JTextField textField_10 = new JTextField();
			textField_10.setBounds(232, 80, 86, 20);
			add(textField_10);
			textField_10.setColumns(10);
			
			JLabel lblWymiary = new JLabel("Wymiary:");
			lblWymiary.setBounds(201, 13, 46, 14);
			add(lblWymiary);
			
			JButton btnDopasuj = new JButton("Dopasuj");
			btnDopasuj.setBounds(211, 119, 110, 23);
			btnDopasuj.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					double aVal = 0, dVal = 0, x = 0, y = 0;
					
					if (rdbtnGrafikaWektorowa.isSelected()) {
						if (transformWekt == null) JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
						else {
							
							try {
								x = Double.parseDouble(textField_1.getText());
								y = Double.parseDouble(textField_10.getText());
								
								aVal = x / secondPanel.dajWielkoscX();
								dVal = y / secondPanel.dajWielkoscY();
							}
							catch (Exception ex) {
								if (secondPanel.im != null || secondPanel.linie != null) {
									aVal = otherPanel.getWidth() / secondPanel.dajWielkoscX();
									dVal = otherPanel.getHeight() / secondPanel.dajWielkoscY();
								}
							}
							
							transformWekt = transformation.przeskaluj(transformWekt, aVal, dVal, 0D, 0D);
							// TODO secondPanel.ustawWektorowa(transformWekt);
						}
					}
					else {
						if (transform == null) JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
						else {
							
							try {
								x = Double.parseDouble(textField_1.getText()) - 1;
								y = Double.parseDouble(textField_10.getText()) - 1;
								
								aVal = x / (transform.getWidth(secondPanel) - 1);
								dVal = y / (transform.getHeight(secondPanel) - 1);
							}
							catch (Exception ex) {
								if (secondPanel.im != null || secondPanel.linie != null) {
									aVal = otherPanel.getWidth() / secondPanel.dajWielkoscX();
									dVal = otherPanel.getHeight() / secondPanel.dajWielkoscY();
								}
							}
							
							transform = transformation.przeskaluj((BufferedImage) transform, aVal, dVal, (int) x + 1, (int) y + 1);
							// TODO secondPanel.ustawRastrowa(transform);
						}
					}
				}
			});
			add(btnDopasuj);
		}
	}
	
	class Rotate extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField cell_00;
		private JTextField cell_01;
		private JTextField aVal;
		private JTextField bVal;
		private JTextField phiValue;
		private JRadioButton rdbtnCenter;
		private JRadioButton rdbtnPoint;
		
		public Rotate() {
			setBackground(SystemColor.activeCaption);
			setLayout(null);
			
			JLabel lblWartoscKata = new JLabel("Warto\u015B\u0107 k\u0105ta:");
			lblWartoscKata.setBounds(10, 10, 88, 20);
			add(lblWartoscKata);
			
			cell_00 = new JTextField();
			cell_00.setEditable(false);
			cell_00.setBounds(175, 41, 42, 20);
			add(cell_00);
			
			cell_01 = new JTextField();
			cell_01.setEditable(false);
			cell_01.setBounds(176, 10, 41, 20);
			add(cell_01);
			
			JButton rotateButton = new JButton("Obróæ");
			rotateButton.setBounds(10, 139, 145, 23);
			rotateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rotate();
				}
			});
			add(rotateButton);
			
			phiValue = new JTextField();
			phiValue.setBounds(44, 34, 48, 20);
			phiValue.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent arg0) {}
				
				@Override
				public void keyReleased(KeyEvent arg0) {}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER) rotate();
				}
			});
			add(phiValue);
			
			JLabel lblNewLabel = new JLabel("\u03C6:");
			lblNewLabel.setBounds(20, 37, 12, 14);
			add(lblNewLabel);
			
			JLabel lblCos_1 = new JLabel("cos \u03C6:");
			lblCos_1.setBounds(133, 40, 31, 14);
			add(lblCos_1);
			
			JLabel lblSin = new JLabel("sin \u03C6:");
			lblSin.setBounds(135, 13, 31, 14);
			add(lblSin);
			
			aVal = new JTextField();
			aVal.setBounds(208, 104, 35, 20);
			add(aVal);
			aVal.setColumns(10);
			
			bVal = new JTextField();
			bVal.setBounds(153, 104, 35, 20);
			add(bVal);
			bVal.setColumns(10);
			
			aVal.setEnabled(false);
			bVal.setEnabled(false);
			
			ButtonGroup group = new ButtonGroup();
			
			rdbtnCenter = new JRadioButton("Wzglêdem œrodka");
			rdbtnCenter.setBounds(10, 68, 111, 23);
			rdbtnCenter.setSelected(true);
			rdbtnCenter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (rdbtnCenter.isSelected()) {
						aVal.setEnabled(false);
						bVal.setEnabled(false);
					}
				}
			});
			add(rdbtnCenter);
			
			rdbtnPoint = new JRadioButton("Wzglêdem punktu");
			rdbtnPoint.setBounds(10, 103, 111, 23);
			rdbtnPoint.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					aVal.setEnabled(true);
					bVal.setEnabled(true);
				}
			});
			add(rdbtnPoint);
			
			group.add(rdbtnCenter);
			group.add(rdbtnPoint);
			
			JLabel lblA = new JLabel("a:");
			lblA.setBounds(143, 107, 12, 14);
			add(lblA);
			
			JLabel lblB = new JLabel("b:");
			lblB.setBounds(198, 107, 12, 14);
			add(lblB);
		}
		
		private void rotate() {
			double x, y;
			
			if (rdbtnGrafikaWektorowa.isSelected()) {
				
				if (rdbtnCenter.isSelected()) {
					if (secondPanel.im != null || secondPanel.linie != null) {
						x = secondPanel.dajWielkoscX() / 2D;
						y = secondPanel.dajWielkoscY() / 2D;
					}
					else return;
				}
				else {
					try {
						x = Integer.parseInt(aVal.getText());
						y = Integer.parseInt(bVal.getText());
					}
					catch (NumberFormatException e) {
						aVal.setText(0 + "");
						bVal.setText(0 + "");
						x = 0;
						y = 0;
					}
				}
				
				if (transformWekt == null) {
					JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
					return;
				}
				try {
					double phi = Double.parseDouble(phiValue.getText());
					
					cell_00.setText(round(Math.cos(phi)) + "");
					cell_01.setText(round(Math.sin(phi)) + "");
					
					transformWekt = transformation.obroc(transformWekt, phi, x, y);
				}
				catch (NumberFormatException numberFormatException) {
					JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych!");
					phiValue.setText("0");
					return;
				}
				// TODO secondPanel.ustawWektorowa(transformWekt);
			}
			else {
				x = transform.getWidth(Rotate.this) / 2;
				y = transform.getHeight(Rotate.this) / 2;
				
				if (transform == null) {
					JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
					return;
				}
				try {
					double phi = Double.parseDouble(phiValue.getText());
					
					cell_00.setText(round(Math.cos(phi)) + "");
					cell_01.setText(round(Math.sin(phi)) + "");
					
					transform = transformation.obroc((BufferedImage) transform, phi, x, y);
				}
				catch (NumberFormatException numberFormatException) {
					JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych!");
					phiValue.setText("0");
					return;
				}
				// TODO secondPanel.ustawRastrowa(transform);
			}
		}
		
		private double round(double value) {
			return new BigDecimal(value).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	class AnyMatrix extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private ArrayList<JTextField> matrixFields;
		
		/** Create the panel.
		 * 
		 * @param secondPanel */
		public AnyMatrix() {
			matrixFields = new ArrayList<JTextField>();
			setBackground(SystemColor.activeCaption);
			setLayout(null);
			
			JLabel label = new JLabel("Wartoœci przekszta³cenia:");
			label.setBounds(10, 10, 130, 20);
			add(label);
			
			JTextField textField = new JTextField();
			textField.setBounds(40, 40, 30, 20);
			add(textField);
			matrixFields.add(textField);
			
			JTextField textField_6 = new JTextField();
			textField_6.setBounds(80, 40, 30, 20);
			add(textField_6);
			matrixFields.add(textField_6);
			
			JTextField textField_3 = new JTextField();
			textField_3.setBounds(120, 40, 30, 20);
			add(textField_3);
			matrixFields.add(textField_3);
			
			JTextField textField_7 = new JTextField();
			textField_7.setBounds(40, 80, 30, 20);
			add(textField_7);
			matrixFields.add(textField_7);
			
			JTextField textField_4 = new JTextField();
			textField_4.setBounds(80, 80, 30, 20);
			add(textField_4);
			matrixFields.add(textField_4);
			
			JTextField textField_8 = new JTextField();
			textField_8.setBounds(120, 80, 30, 20);
			add(textField_8);
			matrixFields.add(textField_8);
			
			JTextField textField_5 = new JTextField();
			textField_5.setBounds(40, 120, 30, 20);
			add(textField_5);
			matrixFields.add(textField_5);
			
			JTextField textField_2 = new JTextField();
			textField_2.setBounds(80, 120, 30, 20);
			add(textField_2);
			matrixFields.add(textField_2);
			
			JTextField textField_9 = new JTextField();
			textField_9.setBounds(120, 120, 30, 20);
			add(textField_9);
			matrixFields.add(textField_9);
			
			JButton wczytajButton = new JButton("Wczytaj");
			wczytajButton.setBounds(168, 40, 145, 23);
			wczytajButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					readMatrixFromFile();
				}
			});
			add(wczytajButton);
			
			JButton anyMatrixButton = new JButton("Przekszta\u0142\u0107");
			anyMatrixButton.setBounds(168, 80, 145, 23);
			anyMatrixButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					double mac[][] = new double[3][3];
					try {
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++)
								mac[i][j] = Double.parseDouble(matrixFields.get(i * 3 + j).getText());
						}
					}
					catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych!");
						return;
					}
					
					if (rdbtnGrafikaWektorowa.isSelected()) {
						if (transformWekt == null) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
							return;
						}
						transformWekt = transformation.dowolna(transformWekt, mac);
						if (transformWekt == null) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Obrazek wielkosci 0!");
							return;
						}
						// TODO secondPanel.ustawWektorowa(transformWekt);
					}
					else {
						if (transform == null) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Brak obrazu!");
							return;
						}
						transform = transformation.dowolna((BufferedImage) transform, mac);
						if (transform == null) {
							JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "Obrazek wielkosci 0!");
							return;
						}
						// TODO secondPanel.ustawRastrowa(transform);
					}
				}
			});
			add(anyMatrixButton);
		}
		
		private void readMatrixFromFile() {
			File fileToConvert = null;
			JFileChooser c = new JFileChooser("C:\\");
			int selectedFile = c.showDialog(Swing2.mainApplicationFrame, "Wybierz");
			if (selectedFile == JFileChooser.APPROVE_OPTION) fileToConvert = c.getSelectedFile();
			
			try {
				Scanner sc = new Scanner(fileToConvert);
				
				for(int i = 0; i < 9; i++)
					matrixFields.get(i).setText(" " + sc.nextDouble());
				
				sc.close();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(Swing2.mainApplicationFrame, "B\u0142\u0105d danych w pliku!");
			}
		}
	}
}