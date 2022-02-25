import fr.cnrs.mri.ij.labelling.NeuriteLabellingTool;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

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
 * The plugin expects a 16-bit image containing a labelled mask of somas and the neurites
 * between the somas as a mask with the value 65535. It will set each neurite pixel
 * to the label of the closest soma on any path along the neurites.
 *
 * @author Volker Baecker
 * @version 24.02.2022
 */
public class Neurite_Labelling_PlugIn implements PlugInFilter {
	ImagePlus image;
	/**
	 * Setup the plugin. The plugin handles 16-bit images. The image must be a labelled mask for the somas and 65535 for the neurites. 
	 * 
	 * @param arg unused, the plugin does not take or need any parameters
	 * @param imp the image on which the labelling will be done
	 * @return the return value tells ImageJ that a 16-bit image is needed as input 
	 */
	@Override
	public int setup(String arg, ImagePlus imp) {
		this.image = imp;
		return DOES_16;
	}
	
	/**
	 * Run the plugin, for big images this may take some time. A 10000x10000 pixel image is expected to be processed in approximately 1 to 10 minutes.
	 * 
	 * @param ip the image processor of the image (passed by the plugin mechanism, but unused).
	 */
	@Override
	public void run(ImageProcessor ip) {
		NeuriteLabellingTool neuriteLabellingTool = new NeuriteLabellingTool(this.image);
		neuriteLabellingTool.run();
		this.image.updateAndDraw();
	}
	
	/**
	 * For manual testing purposes. Do not use this class in your code, use the NeuriteLabellingTool
	 * instead.
	 *
	 * @param args unused, no parameters are needed
	 * @see NeuriteLabellingTool
	 */
	public static void main(String[] args) {
		Opener opener = new Opener();
		ImagePlus image = opener.openImage("/media/baecker/DONNEES/mri/in/neuron-segmentation/StartImage2.tif");
		image.show();
		Neurite_Labelling_PlugIn nlp = new Neurite_Labelling_PlugIn();
		nlp.setup("", image);
		nlp.run(image.getProcessor());
	}
}
