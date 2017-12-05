package org.dlearn.helsinki.skeleton.mentor;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import org.apache.commons.lang3.ArrayUtils;

import org.dlearn.helsinki.skeleton.mentor.Neighbors;


/* A Java class implementation of the paper
 * www.dbs.ifi.lmu.de/Publikationen/Papers/LOF.pdf
 */
public class LocalOutlierFactor {

	/* Returns the euclidean distance of double arrays u and v
	 * 
	 * @param double[] u
	 * @param double[] v
	 * @returns double result, euclidean distance between u and v
	 */
    public double euclidean(double[] u, double[] v) {
        double result = 0.0;

        if (u.length != v.length) {
            return result;
        }

        for (int i = 0; i < u.length; i++) {
            result += Math.pow((u[i] - v[i]), 2);
        }

        return result;
    }

    // Sort by column method missing
    public double[][] kNearestNeighbors(int k, double[] p, double[][] data) {
        // last index reserved for distances
        double[][] neighbors = new double[data.length][data[0].length + 1];
        ArrayUtils.removeElement(neighbors, p);
        int h = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (Arrays.equals(p, data[i])) {
                continue;
            }
            for (int j = 0; j < data[i].length; j++) {
                neighbors[h][j] = data[i][j];
            }
            neighbors[h][data[i].length] = euclidean(p, data[i]);
            h++;
        }
        //sort(neighbors, by=last element of every row);
        //k_dist is the last element of the last row
        return LocalOutlierFactor.slice(neighbors, 0, k);
    }

    /* According to the paper rechability distance of p and o is the maximum between
     * distance(p, o) and k_dist given by the kNearestNeighbors
     */
    public double rechabilityDistance(int k, double[] p, double[] o,
            double[][] data) {
        double[][] neighbors = this.kNearestNeighbors(k, o, data); // does not care if p present
        int n = neighbors.length - 1;
        int m = neighbors[n].length - 1;
        double kDist = neighbors[n][m];
        double distance = this.euclidean(p, o);
        return Math.max(kDist, distance);
    }

    /* Another mathematical expression in the paper
     *
     */
    public double localReachabilityDensity(int k, double[] p, double[][] data) {
    	double lrd = 0.0;
    	double sum = 0.0;
    	double[] neighbors = this.kNearestNeighbors(k, p, data);
    	double[] reachDistances = new double[neighbors.length];
    	for(int i = 0; i < reachDistances.length; i++) {
    		reachDistances[i] = this.reschabilityDistance(k, p, neighbors[i], data);
    	}
    	for (double e : reachDistances) sum += e;
    	lrd = neighbors.length / sum;
        return lrd;
    }

    // TODO
    public double localOutlierFactor() {
        double lof = 0.0;
        return lof;
    }

    // TODO
    public void outliers(int minPts, double[][] data) {
    }

    /* Didn't find a method for double[][] slicing.
     * Slices a double[][] by rows
     */
    public static double[][] slice(double[][] data, int start, int end) {
        
        if (end <= start) {
            return null;
        }

        if (start > 0) {
        	return null;
        }

        if (end > data.length) {
        	end = data.length;
        }

        int k = 0;
        int length = end - start;
        double[][] result = new double[length][data[0].length];
        
        for (int i = start; i < end; i++) {
            result[k] = data[i];
            k++;
        }
        
        return result;
    }
}