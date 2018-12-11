package slagalica;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class dugmeSaSlikom extends JButton
{
	static BufferedImage[] komadici;
	BufferedImage komad;
	public static int brojKomadica;
	boolean imamSliku = false;
	
	
	public static void pripremi(File sslika)
	{
		BufferedImage slika = null;
		try 
		{
			slika = ImageIO.read(sslika);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		komadici = new BufferedImage[brojKomadica];
		
		int sirinaKom = slika.getWidth() / (int)Math.sqrt(brojKomadica);
		int visinaKom = slika.getHeight() / (int)Math.sqrt(brojKomadica);
		
		int brSlike = 0;
		
		for (int kY=0; kY < slika.getHeight() - 5; kY += visinaKom)
		{
			System.out.println("Y: " + kY);
			for (int kX=0; kX < slika.getWidth() - 5; kX += sirinaKom)
			{
				System.out.println("X: " + kX);
				komadici[brSlike] = slika.getSubimage(kX, kY, sirinaKom, visinaKom);
				brSlike++;
			}
			
		}
	}
	
	public dugmeSaSlikom(String s, boolean slika)
	{
		super(s);
		if (slika)
		{
			this.imamSliku = true;
			this.komad = komadici[Integer.parseInt(this.getText()) - 1];
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (this.isEnabled() && this.imamSliku)
			g.drawImage(komad, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
