package es.flabo.utils;

import java.io.File;
import java.io.IOException;

public class Controller {

	private static final String EMPTY = "";
	private static final int MISING_PARAM_ERROR_CODE=-1;
	private static final int FILE_NOT_EXIST_ERROR_CODE=-2;
	private static final int PATH_ERROR_CODE=-3;
	private static final int OUTPUT_EXIST_ERROR_CODE=-3;
	public static final int PROCESS_ERROR_CODE=-5;
	
	/**
	 * Calculate the pixels variation between two images.
	 * Prints the value of variation points or an error code.
	 *   - If the number is higher than 0 then is the variation.
	 *   - if the number is negative is an error:
	 *       + -1 means 'Missing parameters: image1 image2 outputImage'
	 *       + -2 means 'The files does not exist'
	 *       + -3 means 'Error creating the output path'
	 *       + -4 means 'Unexpected error during comparison'
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length<2 || (args[0]==null || args[0].equals(EMPTY) || args[1]==null || args[1].equals(EMPTY)) ){
			System.out.println(MISING_PARAM_ERROR_CODE);
		}else{					
			File image1 =  new File(args[0]);
			File image2 = new File (args[1]);
			if (!image1.exists() || !image2.exists()){
				System.out.println(FILE_NOT_EXIST_ERROR_CODE);
			}else{			
				File output = null;
				int variation = 0;
				
				if (args.length>=3 && args[2]!=null && !args[2].equals(EMPTY)){
					output = new File (args[2]);	
					if (output.exists()){
						System.out.println(OUTPUT_EXIST_ERROR_CODE);
					}else{
						try {
							Util.makeDirs(args[2]);
							variation = Util.getVariation(image2, image1, output);
						} catch (IOException e) { 
							System.out.println(PATH_ERROR_CODE);
						}						
					}					
				}else{
					variation = Util.getVariation(image2, image1, null);
				}							
				System.out.println(variation);
			}
		}
	}

}
