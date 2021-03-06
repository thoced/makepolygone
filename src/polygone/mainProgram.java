package polygone;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import GUI.JPanelDraw;

import java.awt.BorderLayout;

public class mainProgram {

	private JFrame frame;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem menuEcriture;
	private JPanelDraw pDraw;
	private JMenuItem mFermer;
	private JMenu menuEdition;
	private JMenuItem mClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainProgram window = new mainProgram();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainProgram() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setTitle("Make Polygone");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.GRAY);
		
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		
		JMenu menuFichier = new JMenu("Fichier");
		menu.add(menuFichier);
		
		mntmNewMenuItem = new JMenuItem("Ouvrir un calque");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(pDraw!=null)
				{
				// on vérifie si un pdraw est déja ouvert, si oui un message d'avertissement est affiché.
					int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous ouvrir une nouvelle image, les modifications apportées seront perdues si vous n'enregistrer pas le polygone");
					if(result != JOptionPane.OK_OPTION)
						return;
				}
				
				JFileChooser choose = new JFileChooser();
				int result = choose.showOpenDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					File fileCalque = choose.getSelectedFile();
				
					// modification du titre de l'application avec ajout du nom du fichier ouvert
					
					frame.setTitle(frame.getTitle() +  " Fichier ouvert : " + fileCalque.getName());
					
					try
					{
						// suppression du pDraw si il existe deja dans le contenair
						if(pDraw != null)
						{
							frame.getContentPane().remove(pDraw);
							pDraw = null;
						}
						
						// creation du panel drawJPanelDra
						 pDraw = new JPanelDraw(fileCalque);
						
						// ajout dans le contenair
						frame.getContentPane().add(pDraw,BorderLayout.CENTER);
						
						frame.repaint();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Le fichier image n'existe pas ou une erreur est survenue");
						
					}
					
				}
				
			}
		});
		menuFichier.add(mntmNewMenuItem);
		
		menuEcriture = new JMenuItem("Enregistrer le fichier polygone");
		menuEcriture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser choose = new JFileChooser();
				
				int result = choose.showSaveDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					File file = choose.getSelectedFile();
					try
					{
						pDraw.writeFile(file);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(frame, "probleme d'ecriture");
					}
				}
				
				
			}
		});
		menuFichier.add(menuEcriture);
		
		mFermer = new JMenuItem("Fermer");
		mFermer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				frame.dispose();
			}
		});
		menuFichier.add(mFermer);
		
		menuEdition = new JMenu("Edition");
		menu.add(menuEdition);
		
		mClear = new JMenuItem("Effacer le polygone");
		mClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				// appel méthode d'effacement du polygone
				if(pDraw!=null)
				pDraw.clearPolygone();
			}
		});
		menuEdition.add(mClear);
		
		
	}

}
