package slagalica;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class DugmeSaSlikom extends JButton 
{
	static BufferedImage[] slicice;
	boolean saSlikom = false;
	
	public static void pripremiSlicice(int brojKomadica, File nekaSlika)
	{
		BufferedImage slika = null;
		try 
		//asdasdasd
		{
			slika = ImageIO.read(nekaSlika);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		slicice = new BufferedImage[brojKomadica];
		int sirinaSlike = slika.getWidth() / (int)Math.sqrt(brojKomadica);
		int visinaSlike = slika.getHeight() / (int)Math.sqrt(brojKomadica);
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < brojKomadica; i++)
		{
			if (x + sirinaSlike > slika.getWidth())
			{
				x = slika.getWidth() - sirinaSlike;
			}
			
			if (y + visinaSlike > slika.getHeight())
			{
				y = slika.getHeight() - visinaSlike;
			}
			
			slicice[i] = slika.getSubimage(x, y, sirinaSlike, visinaSlike);
			
			if (i%Math.sqrt(brojKomadica) == Math.sqrt(brojKomadica) - 1)
			{
				x = 0;
				y += visinaSlike;
			} else
			{
				x += sirinaSlike;
			}
		}
	}
	
	public DugmeSaSlikom(String naziv, boolean saSlikom)
	{
		super(naziv);
		this.saSlikom = saSlikom;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (this.saSlikom)
		{
			try
			{
				g.drawImage(slicice[Integer.parseInt(this.getText()) - 1], 0, 0, this.getWidth(), this.getHeight(), this);
			} catch (NumberFormatException e) {}
			
		}
	}
}
