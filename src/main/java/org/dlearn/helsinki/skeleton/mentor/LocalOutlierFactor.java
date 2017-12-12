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

public class LocalOutlierFactor {

    public Tuple<Double, List<List<Answer>>> kNearestNeighbors(int k,
            List<Answer> p, List<List<Answer>> data) {
        Map<List<Answer>, Double> distances = new HashMap(data.size());
        for (List<Answer> o : data) {
            if (p.equals(o)) {
                continue;
            }
            distances.put(o, euclidean(p, o));
        }
        List<List<Answer>> neighbors = new ArrayList(k);
        distances = sortMapByValue(distances);
        int i = 0;
        for (List<Answer> q : distances.keySet()) {
            if (i == k)
                break;
            neighbors.add(q);
            i++;
        }
        double kDist = distances.get(neighbors.get(0));
        return new Tuple(kDist, neighbors);
    }

    public double rechabilityDistance(int k, List<Answer> p, List<Answer> o,
            List<List<Answer>> data) {
        double kDist = this.kNearestNeighbors(k, o, data).first();
        double distance = Distance.euclidean(p, o);
        return Math.max(kDist, distance);
    }

    public double localReachabilityDensity(int k, List<Answer> p,
            List<List<Answer>> data) {
        double lrd = 0.0;
        double sum = 0.0;
        int i = 0;
        List<List<Answer>> neighbors = this.kNearestNeighbors(k, p, data)
                .second();
        int n = neighbors.size();
        double[] reachDistances = new double[n];
        for (List<Answer> o : neighbors) {
            reachDistances[i] = this.rechabilityDistance(k, p, o, data);
            i++;
        }
        for (double e : reachDistances) {
            sum += e;
        }
        lrd = n / sum;
        return lrd;
    }

    public double localOutlierFactor(int k, List<Answer> p,
            List<List<Answer>> data) {
        double lof = 0.0;
        double sum = 0.0;
        int i = 0;
        List<List<Answer>> neighbors = this.kNearestNeighbors(k, p, data)
                .second();
        int n = neighbors.size();
        double lrd_p = this.localReachabilityDensity(k, p, data);
        double[] lrd_ratios = new double[n];
        for (List<Answer> o : neighbors) {
            lrd_ratios[i] = this.localReachabilityDensity(k, o, data) / lrd_p;
            i++;
        }
        for (double e : lrd_ratios) {
            sum += e;
        }
        lof = sum / n;
        return lof;
    }

    public Map<Integer, Double> outliers(int minPts, List<Answer> rawData) {
        List<List<Answer>> data = this.prepareData(rawData);
        Map<Integer, Double> outliers = new HashMap();
        for (List<Answer> p : data) {
            double lof = this.localOutlierFactor(minPts, p, data);
            if (lof > 1.0) {
                outliers.put(p.get(0).getStudent_id(), lof);
            }
        }
        outliers = sortMapByValueReverse(outliers);
        return outliers;
    }

    // Remove List<Answer> from List<List<Answer>> data, if Answer.getAnswer() is null;
    public List<List<Answer>> prepareData(List<Answer> rawData) {
        List<Answer> tmp = new ArrayList(rawData);
        List<List<Answer>> data = new ArrayList();
        List<Integer> students = new ArrayList();
        List<Integer> surveys = new ArrayList();
        List<Integer> questions = new ArrayList();
        // find out all the student_id:s, survey_id:s and question_ids:
        for (Answer ans : rawData) {
            if (!students.contains(ans.getStudent_id()))
                students.add(ans.getStudent_id());
            if (!surveys.contains(ans.getSurvey_id()))
                surveys.add(ans.getSurvey_id());
            if (!questions.contains(ans.getQuestion_id()))
                questions.add(ans.getQuestion_id());
        }
        // Loop through every possible student_id
        for (Integer student : students) {
            // Store all answers with same student_id into same List
            List<Answer> studentAnswers = new ArrayList();
            // Loop through List<Answer> tmp
            for(Answer ans : tmp) {
                /* If student answer is found, add it into
                   studentAnswers and remove it from tmp list
                */
                if(student == ans.getStudent_id()) {
                    studentAnswers.add(ans);
                    tmp.remove(ans);
                }
            }
            Collections.sort(studentAnswers, new AnswerComparator());
            data.add(studentAnswers);
        }
        return data;
    }
}