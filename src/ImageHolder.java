import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ImageHolder extends JPanel {

	BufferedImage mainImg;
	BufferedImage originalimg;
	PixelInfo pixelinfo;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1097142146118358854L;

	public ImageHolder(){

	}
	public ImageHolder(BufferedImage img){
		
		mainImg = img;
		originalimg = img;
		//scrollpane.add(this);
		pixelinfo = new PixelInfo(img);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mainImg,0,0,null);
	}
	public void setImg(BufferedImage img){
		mainImg = img;
		this.repaint();
	}

	public BufferedImage getImg(){
		if(mainImg!=null){
			return mainImg;
		}
		else return null;
	}

}
