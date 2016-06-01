package test;

import java.awt.FlowLayout;

public class int_float {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * java默认类型转换测试
		 */
		int wid = 6;
		int height = 3;
		float jj = wid * 3 / (height * 4);
		
		int wid2 = 6;
		int height2 = 6;
		float jj2 = wid2 * 3 / (height2 * 4);
		
		System.out.println("jj:"+jj);
		System.out.println("jj2:"+jj2);
		
		jj = wid *3;
		System.out.println("jj:"+jj);

		jj = jj / (height * 4);
		System.out.println("jj:"+jj);
		
		jj =(float) wid * 3 / (height * 4);
		System.out.println("jj:"+jj);
		
		jj2 = 4/3;
		System.out.println("jj2:"+jj2);
	}

}
