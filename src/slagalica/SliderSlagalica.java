package slagalica;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//Radimo i na predavanju nesto
import java.util.*;
public class SliderSlagalica 
{
	static JFrame frame = new JFrame();
	
	static JButton skrivenoDugme = new JButton("Neko dugme");

	public static void main(String[] args) 
	{
		LayoutManager lm = new GridLayout(3, 3);
		frame.setLayout(lm); 

		for (int i = 0; i < 8; i++)
		{
			dugmeSaSlikom btn = new dugmeSaSlikom(String.valueOf(i+1), false);
			btn.setEnabled(false);
			frame.add(btn);
		}
		skrivenoDugme.setVisible(false);
		frame.add(skrivenoDugme);


		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnTest = new JMenu("Igra");
		menuBar.add(mnTest);
		
		JMenu menuItem = new JMenu("Igra sa brojevima");
		mnTest.add(menuItem);
		
		JMenu menuItem2 = new JMenu("Igra sa slikama");
		mnTest.add(menuItem2);
		
		JMenuItem brojeviLaka = new JMenuItem("Laka 3x3");
		brojeviLaka.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				postaviIgru(3, false);
			}
		});
		menuItem.add(brojeviLaka);
		
		JMenu prikaz = new JMenu("Prikaz");
		menuBar.add(prikaz);
		
		JMenuItem povecaj = new JMenuItem("Povecaj");
		prikaz.add(povecaj);
		
		povecaj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if (frame.getWidth() < Toolkit.getDefaultToolkit().getScreenSize().width &&
						frame.getHeight() < Toolkit.getDefaultToolkit().getScreenSize().height)
				{
					frame.setSize(frame.getWidth() + 20, frame.getHeight() + 20);
					frame.setLocationRelativeTo(null);
				}
			}
		});
		
		povecaj.setAccelerator(KeyStroke.getKeyStroke('+'));
		
		JMenuItem smanji = new JMenuItem("Smanji");
		prikaz.add(smanji);
		
		smanji.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if (frame.getWidth() > 200 &&
				    frame.getHeight() > 200)
				{
					frame.setSize(frame.getWidth() - 20, frame.getHeight() - 20);
					frame.setLocationRelativeTo(null);
				}
			}
		});
		
		smanji.setAccelerator(KeyStroke.getKeyStroke('-'));
		
		
		
		JMenuItem slikeLaka = new JMenuItem("Slike laka 3x3");
		slikeLaka.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				postaviIgru(3, true);
			}
		});
		menuItem2.add(slikeLaka);
		
		//Kazemo nasem frameu da se program ugasi kada se klinke na x
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Postavimo njegovu velicinu
		frame.setSize(600, 600);
		//Maleni trik da se nas prozor centrira :)
		frame.setLocationRelativeTo(null);
		//Stavimo i neki naslov na prozor 
		frame.setTitle("Slider slagalica :)");
		//I, na kraju, kazemo da je vidljiv :)
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void postaviIgru(int dim, boolean slika)
	{
		JFileChooser fc = new JFileChooser();
		dugmeSaSlikom.brojKomadica = dim*dim;
		if (slika)
		{
			fc.showOpenDialog(frame);
			dugmeSaSlikom.pripremi(fc.getSelectedFile());
		}
		
		frame.getContentPane().removeAll();
		int brKomadica = dim*dim;
		
		
		Stack<Integer> nasumicniBrojevi = new Stack<Integer>();

		while (nasumicniBrojevi.size() != brKomadica - 1)
		{				//maksium * Math.random() + minimum
			int broj = (int)((brKomadica - 1) * Math.random() + 1);
	
			if (!nasumicniBrojevi.contains(broj))
			{
				
				nasumicniBrojevi.add(broj);
			}
		} 
		
		skrivenoDugme.setVisible(false);
		
		int gdeJeSkrivenoDugme = (int)((brKomadica - 1) * Math.random());

		for (int i = 0; i < brKomadica; i++)
		{                                   
			
			if (i == gdeJeSkrivenoDugme)
			{
				frame.add(skrivenoDugme);
				continue; 
			}
			
			dugmeSaSlikom dugme;
			if (slika)
			{
				dugme = new dugmeSaSlikom(String.valueOf(nasumicniBrojevi.pop()), true);
			} else
			{
				dugme = new dugmeSaSlikom(String.valueOf(nasumicniBrojevi.pop()), false);
			}
			
			dugme.addActionListener(new ActionListener() 
			{                                      
				                                    
				public void actionPerformed(ActionEvent arg0)
				{
					if ((Math.abs(((Component)arg0.getSource()).getX() - skrivenoDugme.getX()) == skrivenoDugme.getWidth() 
							&& ((Component)arg0.getSource()).getY() == skrivenoDugme.getY()) ||
							(Math.abs(((Component)arg0.getSource()).getY() - skrivenoDugme.getY()) == skrivenoDugme.getHeight() 
							&& ((Component)arg0.getSource()).getX() == skrivenoDugme.getX()))
					{
					
						Point staraLokacija = skrivenoDugme.getLocation();
						
						skrivenoDugme.setLocation(((Component)arg0.getSource()).getLocation());
						
						((Component)arg0.getSource()).setLocation(staraLokacija);
					}
					if (gotovaIgra(brKomadica))
						System.out.println("Pobedaaa!");
				}
			});

			frame.add(dugme); 
		} 
		frame.paintAll(frame.getGraphics());
	}
	public static boolean gotovaIgra(int maks)
	{
		Integer brojDugmeta = 1;
		for (int koordY = 0; koordY <= frame.getHeight() - skrivenoDugme.getHeight(); koordY += skrivenoDugme.getHeight())
		{
			for (int koordX = 0; koordX <= frame.getWidth() - skrivenoDugme.getWidth(); koordX += skrivenoDugme.getWidth())
			{
				if (!((JButton)frame.getContentPane().getComponentAt(koordX, koordY)).getText().equals(brojDugmeta.toString()))
				{
					return false;
				}
				
				if (brojDugmeta == maks - 1)
				{
					return true;
				}
				brojDugmeta++;
			}
		}
		return false;
	}
}
