# images-comparator
Simple library to Calculate the pixels variation between two images.

Prints the value of variation points or an error code.
   - If the number is higher than 0 then is the variation.
   - if the number is negative is an error:
       + -1 means 'Missing parameters: image1 image2 outputImage'
       + -2 means 'The files does not exist'
       + -3 means 'Error creating the output path'
       + -4 means 'Unexpected error during comparison'
       
Execution:
   java -jar image1_full_path image2_full_path (Optional)output_comparison_image
   
 output_comparison_image Is an image created using merging the pixels of the input images to remark visually the changes.
 
