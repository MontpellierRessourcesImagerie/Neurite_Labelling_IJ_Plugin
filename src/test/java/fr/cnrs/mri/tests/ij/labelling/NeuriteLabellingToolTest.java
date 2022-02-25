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
package fr.cnrs.mri.tests.ij.labelling;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.cnrs.mri.ij.labelling.NeuriteLabellingTool;
import ij.ImagePlus;
import ij.gui.NewImage;

class NeuriteLabellingToolTest {
	
	static ImagePlus image;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		image = NewImage.createFloatImage("test-image", 4, 4, 1, NewImage.FILL_BLACK);
		image.getProcessor().set(0, 0, 1);
		image.getProcessor().set(1, 0, 65535);
		image.getProcessor().set(1, 1, 65535);
		image.getProcessor().set(1, 2, 65535);
		image.getProcessor().set(2, 2, 65535);
		image.getProcessor().set(2, 3, 65535);
		image.getProcessor().set(3, 3, 2);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		image.close();
	}

	@Test
	void testNeuriteLabellingTool() {
		NeuriteLabellingTool nlt = new NeuriteLabellingTool(image);
		assertEquals(image, nlt.getImage());
	}

	@Test
	void testGetImage() {
		ImagePlus anImage = new ImagePlus();
		NeuriteLabellingTool nlt = new NeuriteLabellingTool(anImage);
		assertEquals(anImage, nlt.getImage());
	}

	@Test
	void testRun() {
		NeuriteLabellingTool nlt = new NeuriteLabellingTool(image);
		nlt.run();
		ImagePlus resultImage = nlt.getImage();
		assertEquals(1, resultImage.getProcessor().get(0, 0));
		assertEquals(1, resultImage.getProcessor().get(1, 0));
		assertEquals(0, resultImage.getProcessor().get(2, 0));
		assertEquals(0, resultImage.getProcessor().get(3, 0));
		assertEquals(0, resultImage.getProcessor().get(0, 1));
		assertEquals(1, resultImage.getProcessor().get(1, 1));
		assertEquals(0, resultImage.getProcessor().get(2, 1));
		assertEquals(0, resultImage.getProcessor().get(3, 1));
		assertEquals(0, resultImage.getProcessor().get(0, 2));
		assertEquals(1, resultImage.getProcessor().get(1, 2));
		assertEquals(2, resultImage.getProcessor().get(2, 2));
		assertEquals(0, resultImage.getProcessor().get(3, 2));
		assertEquals(0, resultImage.getProcessor().get(0, 3));
		assertEquals(0, resultImage.getProcessor().get(1, 3));
		assertEquals(2, resultImage.getProcessor().get(2, 3));
		assertEquals(2, resultImage.getProcessor().get(3, 3));
	}
}
