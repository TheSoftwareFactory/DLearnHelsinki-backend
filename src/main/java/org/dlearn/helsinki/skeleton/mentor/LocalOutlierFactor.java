package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.List;

public class LocalOutlierFactor {
	public double euclidean(double[] u, double[] v) {
		double result = 0.0;
		
		if(u.length != v.length) {
			return result;
		}
		
		for(int i = 0; i < u.length; i++) {
			result += Math.pow((u[i] - v[i]), 2);
		}
		
		return result;
	}

	public double[][] kNearestNeighbors(int k, double[] p, double[][] data) {
		double k_dist = 0.0;
		double[][] neighbors = new double[data.length][data[0].length];
		return neighbors;
	}

	public static void main(String[] args) {
		double[] u = {1,2,3};
		double[] v = {2,2,3};
		LocalOutlierFactor lof = new LocalOutlierFactor();
		System.out.println(lof.euclidean(u, v));
	}
}