import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class ChromaCombineImg {
	private BufferedImage back;
	private BufferedImage fore;
	private Image backImg;

	public BufferedImage ChromaCombineImg(BufferedImage back, BufferedImage fore){
		int r,g,b,currPixel=0;
		this.fore=fore;
		this.backImg=back;


		backImg = backImg.getScaledInstance(fore.getWidth(), fore.getHeight(), Image.SCALE_SMOOTH);
		this.back = imgToBuffImg(backImg);
		
		for(int x =0;x<this.fore.getWidth();x++){
			for(int y=0;y<this.fore.getHeight();y++){

				currPixel = this.fore.getRGB(x, y);
				r = (currPixel >> 16) & 0x000000FF;
				g = (currPixel >> 8) & 0x000000FF;
				b = currPixel & 0x000000FF;
				
				if(g>=((.85)*(r+b))){
					this.fore.setRGB(x, y, this.back.getRGB(x, y));
				}
			}
		}
		return this.fore;

	}

	public static BufferedImage imgToBuffImg(Image img) {
		//create a new buffered image
		BufferedImage bufimg = new BufferedImage
		(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
		//get the graphics
		Graphics bg = bufimg.getGraphics();
		//draw the image
		bg.drawImage(img, 0, 0, null);
		bg.dispose();
		return bufimg;
	}


}
