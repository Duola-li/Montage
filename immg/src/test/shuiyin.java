package test;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
  
/**  
 * 图片加水印，设置透明度
 * http://blog.csdn.net/hfmbook
 * @author Gary 
 * 创建日期：2014年12月16日 22:45:17
 */  
public class shuiyin {   
  
    /**  
     * @param args  
     */  
	static int width = 800;
	static int height = 600;
	static int beishu = 2;
    public static void main(String[] args) {   
        String srcImgPath = "G:/src.jpg";   
        String iconPath = "G:/head.png";   
        String targerPath = "G:/target.jpg" ; 
         // 给图片添加水印   
        shuiyin.markImageByIcon(iconPath, srcImgPath, targerPath );  
    }   
    /**  
     * 给图片添加水印  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     */  
    public static void markImageByIcon(String iconPath, String srcImgPath,  String targerPath) {   
        markImageByIcon(iconPath, srcImgPath, targerPath, null) ; 
    }   
    /**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印图片旋转角度
     */  
    public static void markImageByIcon(String iconPath, String srcImgPath,   
            String targerPath, Integer degree) {   
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath)); 
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
            // 得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);   
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree),   
                        (double) buffImg.getWidth() / 2, (double) buffImg   
                                .getHeight() / 2);   
            }   
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度  
            ImageIcon imgIcon = new ImageIcon(iconPath);   
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
            float alpha = 0.3f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                    alpha));   
            // 表示水印图片的位置
            int i, j;
            int ddd = 100/beishu;
            for(i=0; i<8*beishu; i++){
            	for(j=0; j<6*beishu; j++){
                    g.drawImage(img, i*ddd, j*ddd, ddd, ddd, null);

            	}
            }
   
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
} 