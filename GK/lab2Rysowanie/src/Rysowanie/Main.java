package Rysowanie;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		 JFrame frame = new JFrame();
//	        JPanel panel = new JPanel();
//	        for (int i = 0; i < 10; i++) {
//	            panel.add(new JButton("Hello-" + i));
//	        }
//	        JScrollPane scrollPane = new JScrollPane(panel);
//	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//	        scrollPane.setBounds(100, 30, 350, 50);
//	        JPanel contentPane = new JPanel(null);
//	        contentPane.setPreferredSize(new Dimension(500, 400));
//	        contentPane.add(scrollPane);
//	        frame.setContentPane(contentPane);
//	        frame.pack();
//	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	        frame.setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameRysowanie();
            }
        });;

	}

}
