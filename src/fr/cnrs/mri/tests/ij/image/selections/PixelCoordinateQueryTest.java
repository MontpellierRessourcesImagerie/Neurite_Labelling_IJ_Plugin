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
package fr.cnrs.mri.tests.ij.image.selections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.cnrs.mri.ij.image.selections.PixelCoordinateQuery;
import ij.ImagePlus;
import ij.gui.NewImage;

class PixelCoordinateQueryTest {

	static ImagePlus image;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		image = NewImage.createFloatImage("test-image", 4, 4, 1, NewImage.FILL_BLACK);
		image.getProcessor().set(0, 1, 1);
		image.getProcessor().set(1, 1, 2);
		image.getProcessor().set(2, 1, 3);
		image.getProcessor().set(3, 1, 4);
		image.getProcessor().set(0, 2, 3);
		image.getProcessor().set(1, 2, 4);
		image.getProcessor().set(2, 2, 5);
		image.getProcessor().set(3, 2, 6);
		image.getProcessor().set(0, 3, 65535);
		image.getProcessor().set(1, 3, 65535);
		image.getProcessor().set(2, 3, 65535);
		image.getProcessor().set(3, 3, 65535);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		image.close();
	}

	/**
	 * Test method for {@link fr.cnrs.mri.ij.image.selections.PixelCoordinateQuery#PixelCoordinateQuery(ij.ImagePlus, float, float)}.
	 */
	@Test
	void testPixelCoordinateQuery() {
		PixelCoordinateQuery pixelCoordinateQuery = new PixelCoordinateQuery(image, 3, 5);
		assertEquals(3, pixelCoordinateQuery.getMin());
		assertEquals(5, pixelCoordinateQuery.getMax());
	}

	/**
	 * Test method for {@link fr.cnrs.mri.ij.image.selections.PixelCoordinateQuery#getCoords()}.
	 */
	@Test
	void testGetCoords() {
		PixelCoordinateQuery pixelCoordinateQuery = new PixelCoordinateQuery(image, 3, 5);
		ArrayList<int[]> coords = pixelCoordinateQuery.getCoords();
		assertEquals(5, coords.size());
		
		pixelCoordinateQuery = new PixelCoordinateQuery(image, 3, 3);
		coords = pixelCoordinateQuery.getCoords();
		assertEquals(2, coords.size());
		assertEquals(3, coords.get(0).length);
		assertEquals(0, coords.get(0)[2]);
		
		pixelCoordinateQuery = new PixelCoordinateQuery(image, 1, 1);
		coords = pixelCoordinateQuery.getCoords();
		assertEquals(1, coords.size());
		assertEquals(0, coords.get(0)[0]);
		assertEquals(1, coords.get(0)[1]);
		assertEquals(0, coords.get(0)[2]);
	}
}
