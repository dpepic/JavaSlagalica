package slagalica;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.util.*;

public class SliderSlagalica 
{
	static JFrame frame = new JFrame();
	static DugmeSaSlikom skrivenoDugme = new DugmeSaSlikom("segsegf", false);
	static int dimenzija = 3;
	static Vector<DugmeSaSlikom> komadici = new Vector<DugmeSaSlikom>();
	
	int test = 5;
	//asdasdasd
	//asdasdasd
	public static void main(String[] args) 
	{

		LayoutManager lm = new GridLayout(dimenzija, dimenzija);
		frame.setLayout(lm); 
		
		for (int i = 0; i < 8; i++)
		{
			DugmeSaSlikom dugme = new DugmeSaSlikom(String.valueOf(i + 1), false);
			dugme.setEnabled(false);
			frame.add(dugme);
		}
		
		skrivenoDugme.setVisible(false);
		frame.add(skrivenoDugme);

		JMenuBar meni = new JMenuBar();
		frame.setJMenuBar(meni);
		
		JMenu izborIgre = new JMenu("Igra");
		meni.add(izborIgre);
		
		JMenu slagalicaSaBrojevima = new JMenu("Nova slagalica sa brojevima");
		izborIgre.add(slagalicaSaBrojevima);
		
		JMenuItem brojeviSuperLaka = new JMenuItem("Za programere (2x2)");
		brojeviSuperLaka.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dimenzija = 2;
				LayoutManager lm = new GridLayout(dimenzija, dimenzija);
				frame.setLayout(lm);
				postaviTablu(false);
				frame.getContentPane().revalidate();
				frame.setLocationRelativeTo(null);
			}
			
		});
		slagalicaSaBrojevima.add(brojeviSuperLaka);
		
		JMenuItem brojeviLaka = new JMenuItem("Laka (3x3)");
		brojeviLaka.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dimenzija = 3;
				LayoutManager lm = new GridLayout(dimenzija, dimenzija);
				frame.setLayout(lm);
				postaviTablu(false);
				frame.setLocationRelativeTo(null);
			}
			
		});
		slagalicaSaBrojevima.add(brojeviLaka);
		
		JMenuItem brojeviSrednja = new JMenuItem("Srednja (4x4)");
		brojeviSrednja.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dimenzija = 4;
				LayoutManager lm = new GridLayout(dimenzija, dimenzija);
				frame.setLayout(lm);
				postaviTablu(false);
				frame.setLocationRelativeTo(null);
			}
			
		});
		slagalicaSaBrojevima.add(brojeviSrednja);
		
		JMenuItem brojeviTeska = new JMenuItem("Teska (5x5)");
		brojeviTeska.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dimenzija = 5;
				LayoutManager lm = new GridLayout(dimenzija, dimenzija);
				frame.setLayout(lm);
				postaviTablu(false);
				frame.setLocationRelativeTo(null);
			}
			
		});
		slagalicaSaBrojevima.add(brojeviTeska);
		
		JMenuItem slagalicaSaSlikama = new JMenuItem("Test za slike");
		slagalicaSaSlikama.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				JFileChooser izborFajla = new JFileChooser(); //OVDE JE BUG!!!!!
				izborFajla.showOpenDialog(frame);
				
				dimenzija = 3;
				DugmeSaSlikom.pripremiSlicice(dimenzija*dimenzija, izborFajla.getSelectedFile());
				LayoutManager lm = new GridLayout(dimenzija, dimenzija);
				frame.setLayout(lm);
				postaviTablu(true);
				frame.setLocationRelativeTo(null);
			}
		});
		izborIgre.add(slagalicaSaSlikama);
		
		
		JMenuItem izlaz = new JMenuItem("Izlaz iz programa");
		izlaz.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);
			}
		});
		izborIgre.addSeparator();
		izborIgre.add(izlaz);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null); 
		frame.setTitle("Slider slagalica :)");
		frame.setVisible(true);
	}
	
	public static void postaviTablu(boolean saSlikom)
	{
		komadici.clear();
		int brKomadica = dimenzija*dimenzija;

		Stack<Integer> nasumicniBrojevi = new Stack<Integer>();

		while (nasumicniBrojevi.size() != brKomadica - 1)
		{				
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
				komadici.add(skrivenoDugme);
				continue; 
			}
			
			DugmeSaSlikom dugme = new DugmeSaSlikom(String.valueOf(nasumicniBrojevi.pop()), saSlikom);

			dugme.addActionListener(new ActionListener() 
			{                                        
				@Override                          
				public void actionPerformed(ActionEvent arg0)
				{			
					int modDugmeta = komadici.indexOf(arg0.getSource()) % dimenzija;
					int modSkriveno = komadici.indexOf(skrivenoDugme) % dimenzija;
					
					if ((Math.abs(modDugmeta - modSkriveno) == 1 && 
					    Math.abs(komadici.indexOf(arg0.getSource()) - komadici.indexOf(skrivenoDugme)) == 1) ||
						Math.abs(komadici.indexOf(arg0.getSource()) - komadici.indexOf(skrivenoDugme)) == dimenzija)
					{
						Collections.swap(komadici, komadici.indexOf(skrivenoDugme), komadici.indexOf(arg0.getSource()));
						overiSlagalicu();
						if (igraGotova())
						{
							JOptionPane.showMessageDialog(frame, "Pobedili ste :)", ":)", JOptionPane.INFORMATION_MESSAGE);
							for (DugmeSaSlikom dugme: komadici)
							{
								dugme.setEnabled(false);
							}
						}
					}
				}
			});
			komadici.add(dugme);
		}
		overiSlagalicu();
	}
	
	public static void overiSlagalicu()
	{
		frame.getContentPane().removeAll();
		for (DugmeSaSlikom dugme: komadici)
		{
			frame.add(dugme);
		}
		frame.validate();
	}
	
	public static boolean igraGotova()
	{
		int trenutniKomad = 1;
		
		for (DugmeSaSlikom dugme: komadici)
		{
			if (dugme.getText().equals(String.valueOf(trenutniKomad)))
			{
				trenutniKomad++;
				if (trenutniKomad == dimenzija*dimenzija)
				{
					return true;
				}
			} else
			{
				return false;
			}
		}
		return false;
	}
}
