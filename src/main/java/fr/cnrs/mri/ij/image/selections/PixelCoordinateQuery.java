package fr.cnrs.mri.ij.image.selections;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.ArrayList;
/**
 * Get the coordinates of all pixels with values in a given range.
 *
 * @author Volker Baecker
 * @version 23.02.2022
 */
public class PixelCoordinateQuery
{
    private ImagePlus image;
    private float min;
    private float max;

    /**
     * Create a PixelCoordinateQuery on image
     */
    public PixelCoordinateQuery(ImagePlus image, float min, float max)
    {
        this.image = image;
        this.min = min;
        this.max = max;
    }

    /**
     * Get the coordinates for the pixels that correspond to the query.
     *
     * @return    A list of coordinates.  Coordinates are int arrays.
     */
    public ArrayList<int[]> getCoords()
    {
        if (min==max) return this.getCoordsOfPixelsWithValueEqualToMin();
        else return this.getCoordsOfPixelsWithValueBetweenMinAndMax();
    }
    
    /**
     * Get the coordinates for the pixels with value between min and max.
     *
     * @return    A list of coordinates.  Coordinates are int arrays.
     */
    protected ArrayList<int[]> getCoordsOfPixelsWithValueBetweenMinAndMax()
    {
        ArrayList<int[]> coords = new ArrayList<int[]>();
        ImageProcessor processor = this.image.getProcessor();
        int width = processor.getWidth();
        int height = processor.getHeight();
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                int value = processor.get(x, y);
                if (value>=min && value<=max) {
                    int[] coord = new int[3];
                    coord[0] = x;
                    coord[1] = y;
                    coord[2] = 0;
                    coords.add(coord);
                }
            }            
        }
        return coords;
    }  
    
    /**
     * Get the coordinates for the pixels with value equal to min.
     *
     * @return    A list of coordinates.  Coordinates are int arrays.
     */
    protected ArrayList<int[]> getCoordsOfPixelsWithValueEqualToMin()
    {
        ArrayList<int[]> coords = new ArrayList<int[]>();
        ImageProcessor processor = this.image.getProcessor();
        int width = processor.getWidth();
        int height = processor.getHeight();
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                int value = processor.get(x, y);
                if (value==min) {
                    int[] coord = new int[3];
                    coord[0] = x;
                    coord[1] = y;
                    coord[2] = 0;
                    coords.add(coord);
                }
            }            
        }
        return coords;
    }  
    
    public float getMin() {
    	return this.min;
    }
    
    public float getMax() {
    	return this.max;
    }
}
