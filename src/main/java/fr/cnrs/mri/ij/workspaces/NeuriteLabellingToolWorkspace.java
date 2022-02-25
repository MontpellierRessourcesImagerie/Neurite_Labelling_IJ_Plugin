package fr.cnrs.mri.ij.workspaces;

import ij.io.Opener;
import ij.ImagePlus;
import fr.cnrs.mri.ij.labelling.NeuriteLabellingTool;

/**
 * Workspace for trying the the NeuriteLabellingTool.
 *
 * @author Volker Baecker
 * @version 23.02.2022
 */
public class NeuriteLabellingToolWorkspace {
	public static void main(String[] args) {
		System.out.println("Starting the NeuriteLabellingToolWorkspace...");
		System.out.println("Opening an image...");
		Opener opener = new Opener();
		ImagePlus image = opener.openImage("/media/baecker/DONNEES/mri/in/neuron-segmentation/StartImage2.tif");
		NeuriteLabellingTool nlt = new NeuriteLabellingTool(image);
		nlt.run();
		image.show();
		System.out.println("Finished the NeuriteLabellingToolWorkspace");
	}
}
