import java.awt.image.BufferedImage;


public class FilterManager  {
	private BufferedImage mainImg;
	private PixelInfo pixelinfo;
	private BufferedImage localImg;
	public FilterManager(BufferedImage img){
		mainImg = img;
		pixelinfo = new PixelInfo(mainImg);
	}
	public BufferedImage applyGreyScale(){
		localImg = new BufferedImage(mainImg.getWidth(),mainImg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		int currentPixelVal=0;
		int alpha = 255 << 24;
		int grey,r,g,b=0;
		for(int x=0;x<mainImg.getWidth();x++){
			for(int y =0;y<mainImg.getHeight();y++){
				grey = (pixelinfo.getRed(x, y)+pixelinfo.getGreen(x, y)+pixelinfo.getBlue(x, y))/3;
				r = (int) (grey) << 16;
				g = (int) (grey) << 8;
				b = (int) (grey);
				currentPixelVal = (alpha)+(r)+(g)+(b);
				localImg.setRGB(x, y, currentPixelVal);
			}
		}
		return localImg;	
	}
	public BufferedImage applySepia(){
		localImg = new BufferedImage(mainImg.getWidth(),mainImg.getHeight(),BufferedImage.TYPE_INT_ARGB);
		final int RANK=3;
		final int DEPTH=20;
		int alpha = 255 << 24;
		int currentPixelVal,grey,r,g,b=0;
		
		for(int x=0;x<mainImg.getWidth();x++){
			for(int y =0;y<mainImg.getHeight();y++){
				r = pixelinfo.getRed(x, y);
				g = pixelinfo.getGreen(x, y);
				b = pixelinfo.getBlue(x, y);
				grey = (r+g+b)/3;
				//sepia
				r=grey+(DEPTH*2);
				g=grey+DEPTH;
				b=grey;
				//outof bounds check
				if(r>255) r=255;
				if(g>255) g=255;
				
				//currentPixelVal = (alpha)+(r<<16)+(g<<8)+(b);
				currentPixelVal = (alpha);
				currentPixelVal |= (r<<16);
				currentPixelVal |= (g<<8);
				currentPixelVal |= (b);
				localImg.setRGB(x, y, currentPixelVal);
			}
		}



		return localImg;	
	}


}
