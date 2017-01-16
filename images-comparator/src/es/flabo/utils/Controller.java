package es.flabo.utils;

import java.io.File;
import java.io.IOException;

public class Controller {

	public static void main(String[] args) {
		
		if (args.length<3){
			System.out.println("Missing parameters: image1 image2 outputImage");
		}else{		
			File image1 =  new File(args[0]);
			File image2 = new File (args[1]);
			if (!image1.exists() || !image2.exists()){
				System.out.println("The files does not exist!");
			}
			
			File output = new File (args[2]);			
			try {
				Util.makeDirs(args[2]);
			} catch (IOException e) { 
				e.printStackTrace();
			}
			
			double variation = Util.getVariation(image2, image1, output);
			System.out.println("Variation:"+variation);
		}
	}

}
