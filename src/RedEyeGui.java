import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class RedEyeGui extends JFrame {

	private BufferedImage mainImg;
	private BufferedImage redEyeImg;
	private ImageHolder redEyeImgHolder;

	private JMenuBar redEyeMenuBar;
	private JMenuItem done;
	private MainGui refMainGui;

	/*
	 * sets up the main Gui, could be instantiated with or without the
	 * main Image and the parent Gui, but it needs the later to do red eye
	 * removal.
	 * 
	 */
	public RedEyeGui(){
		setLayout(new BorderLayout());
		initializeRedEyeJMenu();
		setVisible(true);

	}
	/*
	 * look at the comment section above the main constructor.
	 */
	public RedEyeGui(MainGui parent,BufferedImage img){
		this();
		if(parent!=null){
			this.setSize(parent.getWidth(), parent.getHeight());
		}
		mainImg=img;
		redEyeImgHolder = new ImageHolder(mainImg);
		add(redEyeImgHolder,BorderLayout.CENTER);
		refMainGui = parent;
		redEyeImgHolder.addMouseListener(new RedEyeMouse());
		redEyeImgHolder.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}
	/*
	 * Initializes all the elements needed for the Red Eye GUI 
	 * 
	 */
	private void initializeRedEyeJMenu(){
		redEyeMenuBar = new JMenuBar();
		this.add(redEyeMenuBar, BorderLayout.NORTH);
		done = new JMenuItem("Done");
		done.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RedEyeGui.this.dispose();
				//refMainGui.updateMainGui(mainImg, 1);
				refMainGui.setVisible(true);	
			}

		});
		redEyeMenuBar.add(done);
	}

	private void doRedEyeRemoval(Point one, Point two){
		
		int startX=one.x;
		int startY=one.y;
		int endX=two.x;
		int endY=two.y;

		for(int x = startX; x < endX; x++) {
			for(int y = startY; y < endY; y++) {

				int c = mainImg.getRGB(x,y);
				int  red = (c & 0x00ff0000) >> 16;
			int  green = (c & 0x0000ff00) >> 8;
			int  blue = c & 0x000000ff;

			float redIntensity = ((float)red / ((green + blue) / 2));
			if (redIntensity > 1.5) {
				Color newColor = new Color((green+ blue)/2, green, blue);
				mainImg.setRGB(x, y, newColor.getRGB());
			}
			}
		}
	}

	/*	private class RedEyeMouse implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			LinkedList<Point> clickList = new LinkedList<Point>();
			clickList.add(arg0.getPoint());
			System.out.println("first"+clickList.getFirst()+"last"+clickList.getLast());
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}*/

	private class RedEyeMouse implements MouseListener  {
		Point one, two=null;
		@Override
		public void mouseClicked(MouseEvent e) {

			if(one==null){
				one = e.getPoint();
			}
			else 
			{

				two=e.getPoint();

			}
			if(one!=null & two!=null){
				if((one.getX()>two.getX()) & (one.getY()>two.getY())){
					doRedEyeRemoval(two,one);
				}
				else {
					doRedEyeRemoval(one,two);
				}
				RedEyeGui.this.repaint();
				one=two=null;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}



}
