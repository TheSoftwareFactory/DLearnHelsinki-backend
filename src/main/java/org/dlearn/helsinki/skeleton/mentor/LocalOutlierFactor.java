package org.dlearn.helsinki.skeleton.mentor;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

import org.dlearn.helsinki.skeleton.mentor.AnswerComparator;
import org.dlearn.helsinki.skeleton.mentor.Tuple;
import org.dlearn.helsinki.skeleton.model.Answer;
import static org.dlearn.helsinki.skeleton.mentor.Distance.euclidean;
import static org.dlearn.helsinki.skeleton.mentor.Sort.sortMapByValue;
import static org.dlearn.helsinki.skeleton.mentor.Sort.sortMapByValueReverse;


/* Follows the original paper in implementation
   www.dbs.ifi.lmu.de/Publikationen/Papers/LOF.pdf
*/
public class LocalOutlierFactor {

    /* kNN implementation according to the paper. In the paper this is 
       called k-distance neighborhood of an object p.
       This method returns the comparison k-distance and the neighborhood
       of an object p.
    */
    public Tuple<Double, List<List<Answer>>> kNearestNeighbors(int k,
            List<Answer> p, List<List<Answer>> data) {
        Map<List<Answer>, Double> distances = new HashMap(data.size());
        // Calculate the distances between p and every object in data.
        for (List<Answer> o : data) {
            if (p.equals(o)) {
                continue;
            }
            distances.put(o, euclidean(p, o));
        }
        List<List<Answer>> neighbors = new ArrayList(k);
        /* sort the distances according to the distance to select
           k first distances.
        */
        distances = sortMapByValue(distances);
        int i = 0;
        // Only return k neighbors
        for (List<Answer> q : distances.keySet()) {
            if (i == k)
                break;
            neighbors.add(q);
            i++;
        }
        if (neighbors.size() > 0) {
            double kDist = distances.get(neighbors.get(0));
            return new Tuple(kDist, neighbors);
        } else {
            return new Tuple(null, new ArrayList());
        }
    }

    /* Reachability distance of objects p and o is the maximum between
       distance(p, o) and kDist(o)
    */
    public double rechabilityDistance(int k, List<Answer> p, List<Answer> o,
            List<List<Answer>> data) {
        double kDist = this.kNearestNeighbors(k, o, data).first();
        double distance = Distance.euclidean(p, o);
        return Math.max(kDist, distance);
    }

    /* Calculates the local reachability density of data object p.
    */
    public double localReachabilityDensity(int k, List<Answer> p,
            List<List<Answer>> data) {
        double lrd = 0.0;
        double sum = 0.0;
        // Find out k nearest neighbors of p
        List<List<Answer>> neighbors = this.kNearestNeighbors(k, p, data)
                .second();
        // Calculate the sum of the reachability distances for every neighbor
        for (List<Answer> o : neighbors) {
            double reachDistance = this.rechabilityDistance(k, p, o, data);
            sum += reachDistance;
        }
        /* In the paper this is given in the form 1 / (sum / k), which is
           equal to k / sum
        */
        int n = neighbors.size();
        lrd = n / sum;
        return lrd;
    }

    /* Calculates the local outlier factor of data object p.
       Here the minPts is replaced with k to highlight the 
       role of the parameter w.r.t. kNN.
    */
    public double localOutlierFactor(int k, List<Answer> p,
            List<List<Answer>> data) {
        double lof = 0.0;
        double sum = 0.0;
        List<List<Answer>> neighbors = this.kNearestNeighbors(k, p, data)
                .second();
        int n = neighbors.size();
        if (n == 0)
            return lof;
        double lrd_p = this.localReachabilityDensity(k, p, data);
        for (List<Answer> o : neighbors) {
            double lrd_ratio = this.localReachabilityDensity(k, o, data)
                    / lrd_p;
            sum += lrd_ratio;
        }
        lof = sum / n;
        return lof;
    }

    /* A Method that is called to analyse outliers.
       @param minPts: is the variable introduced in the original paper.
       Changing the value of minPts might give different results and take
       more run time. Proper analysis would take in results with different
       minPts values and present some aggregate as the solution.
       @param int amountOfQuestions: is the size of a desired data object
       in the data List
    
    */
    public Map<Integer, Double> outliers(int minPts, int amountOfQuestions,
            List<Answer> rawData) {
        // Change the dimension of the data
        List<List<Answer>> data = this.prepareData(amountOfQuestions, rawData);
        Map<Integer, Double> outliers = new HashMap();
        if (data.isEmpty())
            return outliers;
        // Calculate the local outlier factor for every object p in data 
        for (List<Answer> p : data) {
            double lof = this.localOutlierFactor(minPts, p, data);
            /* According to the paper: "..for most objects in the cluster
               their LOF are approximately equal to 1." i.e. every object
               with a LOF higher than 1 is a potential outlier.
            */
            if (lof > 1.0) {
                outliers.put(p.get(0).getStudent_id(), lof);
            }
        }
        // Sorts a Map in descending order according to Value
        outliers = sortMapByValueReverse(outliers);
        return outliers;
    }

    /* Method which creates a 2D List of the 1D List.
       All answers with same student_id are grouped into same
       List. Also the 1D lists inside data are sorted by
       survey_id and questions_id, so that comparison in the 
       distance calculation makes sense.
    */
    public List<List<Answer>> prepareData(int amountOfQuestions,
            List<Answer> rawData) {
        List<List<Answer>> data = new ArrayList();
        List<Integer> students = new ArrayList();
        if (rawData.isEmpty())
            return data;
        // find out all the student_id:s in a class
        for (Answer ans : rawData) {
            if (!students.contains(ans.getStudent_id()))
                students.add(ans.getStudent_id());
        }
        // Loop through every possible student_id
        for (Integer student : students) {
            // Store all answers with same student_id into same List
            List<Answer> studentAnswers = new ArrayList();
            // Loop through List<Answer> rawData
            for (Answer ans : rawData) {
                /* If student answer is found, add it into
                   studentAnswers and remove it from tmp list
                */
                if (student == ans.getStudent_id()) {
                    studentAnswers.add(ans);
                }
            }
            /* Only required length studentAnswers are added to the 
               data List. If studentAnswers had different lengths, then
               distance calculation would fail.
            */
            if (studentAnswers.size() == amountOfQuestions
                    && studentAnswers.size() > 0) {
                Collections.sort(studentAnswers, new AnswerComparator());
                data.add(studentAnswers);
            }
        }
        return data;
    }
}
