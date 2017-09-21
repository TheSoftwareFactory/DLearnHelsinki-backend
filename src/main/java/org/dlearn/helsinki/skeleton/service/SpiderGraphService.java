package org.dlearn.helsinki.skeleton.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;

import org.dlearn.helsinki.skeleton.database.Database;
import org.dlearn.helsinki.skeleton.model.SpiderGraph;

public class SpiderGraphService {

    Database db = new Database();

    public SpiderGraphService() {
        super();
    }

    public List<SpiderGraph> getSpiderGraphsFromStudentId(int student_id) {
        return Lists.newArrayList(
            new SpiderGraph(1, 2, 3, 4, 5, 6, 7),
            new SpiderGraph(1, 2, 3, 4, 5, 6, 7)
        );
    }

    public SpiderGraph getSpiderGraphFromid(int student_id, int spidergraph_id) {
        System.out.println("Calling SpiderGraphFromId");
        try {
            return db.getSpiderGraph(student_id, spidergraph_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new SpiderGraph(-1, -1, -1, -1, -1, -1, -1);
    }

    public SpiderGraph updateSpiderGraphFromid(int student_id, int spidergraph_id, SpiderGraph spidergraph) {
        spidergraph.set_id(student_id);
        spidergraph.setStudent_id(student_id);
        SpiderGraph newSpider = new SpiderGraph();
        try {
            newSpider = db.updateSpidergraph(spidergraph);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newSpider;
    }

}
