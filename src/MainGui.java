import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


public class MainGui extends JFrame {




	/**
	 * 
	 */
	private static final long serialVersionUID = 5774405825176801999L;

	private FilterManager filters;
	private RedEyeGui redEyeGui;
	private ChromaKeyGui chromaGui;
	private LinkedList<BufferedImage> imgList;


	private JMenuBar menuBar;
	private ImageHolder ImagePanel;
	private JMenu FileMenu;
	private JMenuItem Open;
	private JMenuItem Save;
	private JMenu FiltersMenu;
	private JMenuItem GreyScale;
	private JMenuItem Sepia;
	private JMenu EffectsMenu;
	private JMenuItem RedEye;
	private JMenuItem Chroma;

	public MainGui(){
		imgList = new LinkedList<BufferedImage>();
		setVisible(true);
		setSize(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		ImagePanel = new ImageHolder();
		this.add(ImagePanel, BorderLayout.CENTER);
		initializeMenuBarContents();
	}

	private void initializeMenuBarContents(){

		menuBar = new JMenuBar();
		add(menuBar,BorderLayout.NORTH);

		FileMenu = new JMenu("File");		
		menuBar.add(FileMenu);

		Open = OpenButtonMenuItem("Open");
		FileMenu.add(Open);
		Save = new JMenuItem("Save");
		Save.addActionListener(new SaveActionListen());
		FileMenu.add(Save);
		FiltersMenu = new JMenu("Filters");
		menuBar.add(FiltersMenu);

		GreyScale =  new JMenuItem("GreyScale");
		FiltersMenu.add(GreyScale);
		Sepia = new JMenuItem("Sepia");
		FiltersMenu.add(Sepia);

		EffectsMenu = new JMenu("effects");
		menuBar.add(EffectsMenu);

		RedEye = new JMenuItem("redEyeRemoval");	
		EffectsMenu.add(RedEye);
		Chroma = new JMenuItem("ChromaKey");	
		EffectsMenu.add(Chroma);
		anonActionListeners();
	}
	/*
	 * All the Action Listeners for various buttons, which apply Filters and effects on images
	 * 
	 */
	private void anonActionListeners(){
		GreyScale.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateMainGui(filters.applyGreyScale());
			}
		});

		Sepia.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateMainGui(filters.applySepia());
			}
		});

		RedEye.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(MainGui.this.ImagePanel.getImg()!=null){
					redEyeGui = new RedEyeGui(MainGui.this,MainGui.this.ImagePanel.getImg());
					MainGui.this.setVisible(false);
				}
			}
		});



		Chroma.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chromaGui = new ChromaKeyGui(MainGui.this);
				MainGui.this.setVisible(false);
				chromaGui.setVisible(true);
				chromaGui.setSize(350, 350);
			}
		});
	}
	/*
	 * if tag == 1 then it adds the img to the history of images for the undo function
	 * 
	 * if order to the use the undo function tag must == 0, so undid changes are not added
	 * to the history of images. 
	 * 
	 */
	public void updateMainGui(BufferedImage img){
		filters = new FilterManager(img);
		MainGui.this.ImagePanel.setImg(img);
		MainGui.this.setSize(img.getWidth(), (int) (img.getHeight()+(menuBar.getHeight()*2.5)));
	}


	private JMenuItem OpenButtonMenuItem(String name){
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(new OpenActionListener());
		return menuItem;
	}

	/*
	 * this is the ActionListener for the Open Image Button under
	 * the File menu. it displays a file chooser over the main GUI.
	 * --Private Class
	 */
	private class OpenActionListener implements ActionListener {

		BufferedImage img;


		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			JFileChooser fileChooser = new JFileChooser();

			try{
				if(fileChooser.showOpenDialog(MainGui.this.getContentPane())== JFileChooser.APPROVE_OPTION){
					File imgFile = fileChooser.getSelectedFile();
					img = ImageIO.read(imgFile);
					if(img!=null){
						filters = new FilterManager(img);
						//MainGui.this.ImagePanel.setImg(img);
						updateMainGui(img);
					}
					else 
						JOptionPane.showMessageDialog(MainGui.this, "Not an Image, try again");
				}
			}
			catch(IOException error){
				JOptionPane.showMessageDialog(MainGui.this, "Failed to Open Picture, Please try again");
			}
		}
	}
	
	/*
	 * saves the image to a Png image when given the name
	 */
	private class SaveActionListen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(ImagePanel.getImg() == null){
				JOptionPane.showMessageDialog(MainGui.this,"no image to save");
				return;
			}
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(MainGui.this);
			File selectFile = fileChooser.getSelectedFile();
			if(selectFile ==null){
				return;
			}
			try{
				BufferedImage image = ImagePanel.getImg();
				ImageIO.write(image, "PNG", selectFile);
			}
			catch(IOException e1){
				JOptionPane.showMessageDialog(MainGui.this, "The file could not be saved");
			}
		}
		
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGui gui = new MainGui();
		gui.setVisible(true);
	}

}
