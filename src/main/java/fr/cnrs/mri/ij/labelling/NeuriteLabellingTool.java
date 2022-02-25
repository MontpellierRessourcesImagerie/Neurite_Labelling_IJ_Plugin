package fr.cnrs.mri.ij.labelling;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.Date;
import fr.cnrs.mri.ij.image.selections.PixelCoordinateQuery;
/**
 * Copyright 2022 INSERM
 *
 * written by Volker Baecker at Montpellier Ressources Imagerie, Biocampus Montpellier, INSERM, CNRS, University of Montpellier (www.mri.cnrs.fr)
 *
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
*/

/**
 * The tool takes an image containing a labelled mask of somas and the neurites
 * between the somas as a mask with the value 65535. It will set each neurite pixel
 * to the label of the closest soma on any path along the neurites.
 *
 * @author Volker Baecker
 * @version 23.02.2022
 */
public class NeuriteLabellingTool
{
    // The input image. The input image will be modified by the tool
    private ImagePlus image;

    /**
     * Constructor for objects of class NeuriteLabellingTool
     */
    public NeuriteLabellingTool(ImagePlus imp)
    {
        this.image = imp; 
    }
    
    /**
     * Get the image on which the tool acts.
     *
     * @return    the image is the input image, which after the execution of run has been modified. 
     */
    public ImagePlus getImage()
    {
        return this.image;
    }
    
    /**
     * Run the tool on the input image.
     */
    public ImagePlus run() {
        Date date = new Date();
        long start = date.getTime();
        System.out.println("Running the NeuriteLabellingTool...");
        System.out.println("Getting the unlabelled pixels...");
        PixelCoordinateQuery query = new PixelCoordinateQuery(this.image, 65535, 65535);
        ArrayList<int[]> unlabelled = query.getCoords();
        System.out.println(unlabelled.size() + " unlabelled pixels found.");
        ImageProcessor processor = this.image.getProcessor();
        ArrayList<int[]> toBeLabelled = new ArrayList<int[]>();
        ArrayList<int[]> stillUnlabelled = new ArrayList<int[]>();
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        int iteration = 1;
        int x, xC;
        int y, yC;
        int value;
        while (true) {
            for (int[] pixel : unlabelled) {
                boolean found = false;
                x = pixel[0];
                y = pixel[1];
                for (byte i=-1; i<=1 && !found; i++) {
                    for (byte j=-1; j<=1 && !found; j++) {
                    	xC = x + i;
                    	yC = y + j;
                    	if (xC<0 || xC>=width || yC<0 || yC>=height) value = 0;
                    	else value = processor.get(x + i, y + j);
                        if (value > 0 && value < 65535) {
                            found = true;
                            pixel[2] = value;
                            toBeLabelled.add(pixel);
                        }
                    }      
                }
                if (!found) stillUnlabelled.add(pixel);
            }
            if (toBeLabelled.isEmpty()) break;
            System.out.println(iteration + ". Labelling " + toBeLabelled.size() + " pixels, "+ stillUnlabelled.size() +" still unlabelled...");
            for (int[] pixel : toBeLabelled) {
                processor.set(pixel[0], pixel[1], pixel[2]);
            }
            toBeLabelled.clear();
            unlabelled = stillUnlabelled;
            stillUnlabelled = new ArrayList<int[]>();
            iteration++;
        }
        date = new Date();
        long end = date.getTime();
        System.out.println("Processing took " + ((end-start)/1000) + "s");
        return this.image;
    }
}
