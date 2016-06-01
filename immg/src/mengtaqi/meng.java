package mengtaqi;

import java.awt.AlphaComposite;
import java.awt.Font;

import org.apache.commons.io.FileUtils;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import test.shuiyin;

public class meng {
	
	public static int WHOLE_WIDTH = 800;
	public static int WHOLE_HEIGHT = 600;
	public static int RATE = 20;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "G:/lbj3mMo.png";
		String tar = "G:/ttt.png";
		String sss = "For  forever LeBron James";
//		zoom(src, tar, WHOLE_WIDTH/RATE, WHOLE_HEIGHT/RATE);
//		System.out.println("success");
		//cut43("G:/head.png",null);
		//Mosaic(new File("g:/1"), src);
		markImageByStr(sss, src, tar, -5);
	}
	
	public static void markImageByStr(String text, String srcImgPath,String targerPath, Integer degree) {   
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath)); 
            BufferedImage buffImg = new BufferedImage(850, 850, BufferedImage.TYPE_INT_RGB); 
         
            Graphics2D g = buffImg.createGraphics(); 
           
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
            
            g.setFont(new Font("Lydia Puente)", Font.PLAIN, 64));
            g.drawString(text, 50, 120);
            
            g.setFont(new Font("Rage Italic LET", Font.PLAIN, 32));
            g.drawString("written by Java", 600, 770);
            g.drawString("Duola 2016/05/24", 570, 810);
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree));   
            }   
            g.drawImage(srcImg, -15, 170, null);   

   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));  
            
            g.dispose();   
            os = new FileOutputStream(targerPath);   
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
	
	public static void Mosaic(File MoPath, String srcImgPath) {   
        OutputStream os = null;  
        if(!MoPath.isDirectory()){//不是文件架
        	System.out.println("eeeee1");
        	return;
        }
        try {   
        	cut43(srcImgPath, "temp.png");
        	zoom("temp.png", "temp2.png", WHOLE_WIDTH, WHOLE_HEIGHT);
        	
            Image srcImg = ImageIO.read(new File("temp2.png")); 
            BufferedImage buffImg = new BufferedImage(WHOLE_WIDTH, WHOLE_HEIGHT, BufferedImage.TYPE_INT_RGB); 
           
            Graphics2D g = buffImg.createGraphics();   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
            g.drawImage(srcImg, 0, 0, null);   
  
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            
            File [] Molist = MoPath.listFiles();
            int l = Molist.length;
            if(l < 1){
            	System.out.println("eeeee2");
            	return ;
            }
            float alpha = 0.3f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha)); 
            int now_num=0;
            int inth = WHOLE_HEIGHT / RATE;
            int intw = WHOLE_WIDTH	/ RATE;
            BufferedImage bufferedImage;
            for(int i=0; i<RATE; i++){
            	for(int j=0; j<RATE; j++){
            		bufferedImage = Zoomcut43(Molist[now_num], intw, inth);
            		now_num = (now_num+1) % l;
            		g.drawImage(bufferedImage, null, i*intw, j*inth);
            	}
            }
        
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));  
            
            g.dispose();   
            os = new FileOutputStream(srcImgPath.replaceAll("\\.\\S*", "Mo.png"));   
            // 生成图片   
            ImageIO.write(buffImg, "png", os);
            System.out.println("done");
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
	public static BufferedImage cut43(String src, String outname){
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(src));
			int sw = srcImg.getWidth(null);
			int tw = sw;
			int sh = srcImg.getHeight(null);
			int th = sh;
			float judje = (float)sw*3 / sh;//宽高比大于4/3则为扁长，高不变切宽，切掉长的那个。//操了，默认类型抓换好像是按照int来的。
			if(judje > 4){
				tw = sh * 4 / 3;
			}else if(judje < 4){
				th = sw * 3 / 4;
			}
	
			BufferedImage buffimg = new BufferedImage(tw, th, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffimg.createGraphics();
			//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
			  
            g.drawImage(srcImg, -(sw - tw)/2, -(sh - th)/2, null);   
  			
  			g.dispose();
  			if(outname != null)
  				os = new FileOutputStream(outname);
  			else
  				os = new FileOutputStream("G:/cuttese.png");
  			ImageIO.write(buffimg, "png", os);
  			System.out.println("cut success");
  			return buffimg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("cut failed");
		return null;
	}
	public static BufferedImage Zoomcut43(File src, int width, int height){
		try {
			Image srcImg = ImageIO.read(src);
			int sw = srcImg.getWidth(null);
			int tw = sw;
			int sh = srcImg.getHeight(null);
			int th = sh;
			float judje = (float)sw*3 / sh;//宽高比大于4/3则为扁长，高不变切宽，切掉长的那个。//操了，默认类型抓换好像是按照int来的。
			if(judje > 4){
				tw = sh * 4 / 3;
			}else if(judje < 4){
				th = sw * 3 / 4;
			}
	
			BufferedImage buffimg = new BufferedImage(tw, th, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffimg.createGraphics();
			//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
            g.drawImage(srcImg, -(sw - tw)/2, -(sh - th)/2, null);   
  			g.dispose();
  			
  			BufferedImage buffImg2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			Graphics2D g2 = buffImg2.createGraphics();
			//g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g2.drawImage(buffimg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null); //把切好的缩放  
  			
  			g2.dispose();
  			
  			
  			//System.out.println("cut success");
  			return buffImg2;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//System.out.println("cut failed");
		return null;
	}
	
	public static void zoom(String src, String tar, int width, int height){
		/*
		 * 缩放图片为指定长宽的png格式
		 */
		OutputStream os = null;
		try{
			Image tarImg = ImageIO.read(new File(src));
			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(tarImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);   
  			
  			g.dispose();
  			os = new FileOutputStream(tar);
  			ImageIO.write(buffImg, "PNG", os);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(null != os)
					os.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
