package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPanelDraw extends JPanel implements MouseListener
{

	// image calque
	private BufferedImage image = null;
	// offset pour centrer
	private int offsetX,offsetY;
	// liste des points du polygone
	private List<PolyPoint> listPolyPoint;
	// Stroke
	private Stroke myStroke;
	
	public JPanelDraw(File fileCalque) throws IOException
	{
		// chargement de l'image
		image = ImageIO.read(fileCalque);
		// précision de la taille de la jpanel
		this.setSize(1024, 768);
		this.setBackground(Color.GRAY);
		
		// création de l'offset
		offsetX = (this.getWidth() / 2) - (this.image.getWidth()/2);
		offsetY = (this.getHeight() / 2) - (this.image.getHeight()/2);
		// creation de la liste poly point
		listPolyPoint = new ArrayList<PolyPoint>();
		// Stroke
		myStroke = new BasicStroke(3.0f);
		
		
		// ajout dans le listener
		this.addMouseListener(this);
		
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
			Graphics2D g2 = (Graphics2D) g;
		
			if(this.image!=null)
			{
				g2.drawImage(this.image,offsetX,offsetY, this.image.getWidth(), this.image.getHeight(), null);
				
				// affichage du polypoint
				if(this.listPolyPoint.size() > 1)
				{
					int[] dx,dy;
					dx = new int[this.listPolyPoint.size()];
					dy = new int[this.listPolyPoint.size()];
					
					int i=0;
					for(PolyPoint p : this.listPolyPoint)
					{
						dx[i] = p.x;
						dy[i] = p.y;
						i++;
					}
					
					g2.setColor(Color.BLUE);
					g2.setStroke(myStroke);
					g2.drawPolyline(dx, dy, this.listPolyPoint.size());
				}
			}
			
			
	}


	


	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		// ajout d'un point
		Point p = arg0.getPoint();
		
		if(p.x > this.offsetX && p.x < this.offsetX + this.image.getWidth())
		{
			if(p.y > this.offsetY && p.y < this.offsetY + this.image.getHeight())
			{
				// le point est bien dans l'image
				PolyPoint point = new PolyPoint();
				point.x = p.x;
				point.y = p.y;
				// ajout du point dans la liste
				this.listPolyPoint.add(point);
				// afficahge
				this.repaint();
			}
		}
		
	}
	
	public void writeFile(File file) throws IOException
	{
		// création du tempon
		StringBuilder text  = new StringBuilder();
		
		for(PolyPoint p : this.listPolyPoint)
		{
			String line = String.format("%d,%d", p.x - this.offsetX,p.y - this.offsetY);
			text.append(line + "\r");
		}
		
		String mem = text.toString();
		
		
		FileOutputStream fos = new FileOutputStream(file);
		
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(mem.getBytes());
		bos.close();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
