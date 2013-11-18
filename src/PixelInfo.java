import java.awt.image.BufferedImage;


public class PixelInfo {
	BufferedImage mainImage;
	public PixelInfo(BufferedImage pic){
		this.mainImage = pic;
		
	}
	public int getRed(int x, int y){
		//return (mainImage.getRGB(x, y) & 0x00ff0000) >> 16;
		return (mainImage.getRGB(x, y) >> 16) & 0x000000ff;
	}
	public int getGreen(int x, int y){
		return (mainImage.getRGB(x, y) >> 8) & 0x000000ff;
	}
	public int getBlue(int x, int y){
		return (mainImage.getRGB(x, y) & 0x000000ff);
	}
}
