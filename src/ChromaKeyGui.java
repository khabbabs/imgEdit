import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class ChromaKeyGui extends JFrame{

	private MainGui mainGui;
	private JSplitPane centerImgPanel;
	private ImageHolder fore;
	private ImageHolder back;
	private JMenuBar menuBar;
	private JMenuItem openFore;
	private JMenuItem openBack;
	private JMenuItem cancel;
	private JMenuItem done;
	private ChromaCombineImg combine;

	public ChromaKeyGui(MainGui parent){
		mainGui=parent;
		setLayout(new BorderLayout());
		initializeChromaJMenu();
		fore = new ImageHolder();
		back = new ImageHolder();
		centerImgPanel = new JSplitPane();
		centerImgPanel.setLeftComponent(fore);
		centerImgPanel.setRightComponent(back);
		add(centerImgPanel,BorderLayout.CENTER);
		combine = new ChromaCombineImg();
	}

	private void initializeChromaJMenu(){
		menuBar = new JMenuBar();
		this.add(menuBar,BorderLayout.NORTH);
		openFore = new JMenuItem("OpenFore");
		menuBar.add(openFore);
		openFore.addActionListener(new openListener("fore"));
		openBack = new JMenuItem("OpenBack");
		menuBar.add(openBack);
		openBack.addActionListener(new openListener("back"));
		cancel = new JMenuItem("cancel");
		menuBar.add(cancel);
		done = new JMenuItem("done");
		menuBar.add(done);

		done.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainGui.setVisible(true);
				if((fore.getImg()!=null)&(back.getImg()!=null)){
					mainGui.updateMainGui(combine.ChromaCombineImg(back.getImg(), fore.getImg()));

					ChromaKeyGui.this.dispose();
				}
				else{

					ChromaKeyGui.this.dispose();
				}
			}

		});
	}

	private class openListener implements ActionListener {
		String location;
		private BufferedImage img;
		public openListener(String holder){
			this.location=holder;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JFileChooser fileChooser = new JFileChooser();
			try{
				if(fileChooser.showOpenDialog(ChromaKeyGui.this)== JFileChooser.APPROVE_OPTION){
					File imgFile = fileChooser.getSelectedFile();
					img = ImageIO.read(imgFile);
					if(img!=null){
						if(location=="fore"){
							ChromaKeyGui.this.fore.setImg(img);
						}
						else if(location=="back"){
							ChromaKeyGui.this.back.setImg(img);
						}
						ChromaKeyGui.this.repaint();
						ChromaKeyGui.this.setSize(img.getWidth()*2, (int) (img.getHeight()+(menuBar.getHeight()*2.5)));
					}
					else 
						JOptionPane.showMessageDialog(ChromaKeyGui.this, "Not an Image, try again");
				}
			}
			catch(IOException error){
				JOptionPane.showMessageDialog(ChromaKeyGui.this, "Failed to Open Picture, Please try again");
			}
		}
	}
}
