# Neurite_Labelling_IJ_Plugin

An ImageJ-plugin to label neurites with the label of the closest soma with respect to the distance along the paths on the neurites. The tool takes an image containing a labelled mask of somas and neurites. The somas should have individual labels, while the neurites must all be labelled  with the value 65535. It will set each neurite pixel to the label of the closest soma on any path along the neurites.

## Installation

Download the file [Neurite_Labelling_IJ_Plugin-0.0.1-20220407.jar](https://github.com/MontpellierRessourcesImagerie/Neurite_Labelling_IJ_Plugin/releases/download/v0.1/Neurite_Labelling_IJ_Plugin-0.0.1-20220407.jar) and save it into the ``plugins``-folder of your Fiji installation.


