package org.dlearn.helsinki.skeleton.mentor;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

import org.dlearn.helsinki.skeleton.model.Answer;
import org.dlearn.helsinki.skeleton.mentor.Tuple;
import static org.dlearn.helsinki.skeleton.mentor.Distance.euclidean;

public class LocalOutlierFactor {

    public Tuple<Double, List<Integer>> kNearestNeighbors(int k, List<Answer> p,
            List<List<Answer>> data) {
        Map<Integer, Double> neighbors = new HashMap(data.size());
        for (List<Answer> o : data) {
            if (p.equals(o)) {
                continue;
            }
            int student_id = o.get(0).getStudent_id();
            neighbors.put(student_id, euclidean(p, o));
        }
        List<Double> distances = new ArrayList(neighbors.values());
        Collections.sort(distances);
        double kDist = distances.get(0);
        return new Tuple(kDist, neighbors);
    }

    public double rechabilityDistance(int k, List<Answer> p, List<Answer> o,
            List<List<Answer>> data) {
        Tuple<Double, List<Integer>> knnResults = this.kNearestNeighbors(k, o,
                data);
        double kDist = knnResults.first();
        double distance = Distance.euclidean(p, o);
        return Math.max(kDist, distance);
    }

    /*
    public double localReachabilityDensity(int k, double[] p, double[][] data) {
        double lrd = 0.0;
        double sum = 0.0;
        double[][] neighbors = this.kNearestNeighbors(k, p, data);
        double[] reachDistances = new double[neighbors.length];
        for (int i = 0; i < reachDistances.length; i++) {
            // remove last element from neighbor
            double[] neighbor = Arrays.copyOfRange(neighbors[i], 0,
                    neighbors[i].length - 1);
            reachDistances[i] = this.rechabilityDistance(k, p, neighbor, data);
        }
        for (double e : reachDistances)
            sum += e;
        lrd = neighbors.length / sum;
        return lrd;
    }
    
    public double localOutlierFactor(int k, double[] p, double[][] data) {
        double lof = 0.0;
        double[][] neighbors = this.kNearestNeighbors(k, p, data);
        double lrd_p = this.localReachabilityDensity(k, p, data);
        double[] lrd_ratios = new double[neighbors.length];
        for (int i = 0; i < lrd_ratios.length; i++) {
            //slice distance element out
            double[] o = Arrays.copyOfRange(neighbors[i], 0,
                    neighbors[i].length - 1);
            lrd_ratios[i] = this.localReachabilityDensity(k, o, data) / lrd_p;
        }
        double sum = 0.0;
        for (double e : lrd_ratios)
            sum += e;
        lof = sum / neighbors.length;
        return lof;
    }
    
    // TODO
    public void outliers(int minPts, double[][] data) {
    }
    */
    public static double[][] slice(double[][] data, int start, int end) {
        if (end <= start) {
            return null;
        }
        if (start < 0) {
            return null;
        }
        if (end > data.length) {
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