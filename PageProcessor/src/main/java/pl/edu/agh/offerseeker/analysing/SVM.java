package pl.edu.agh.offerseeker.analysing;

import libsvm.*;

import java.util.ArrayList;
import java.util.List;

public class SVM {
    private svm_parameter _param;
    private svm_model _svm;

    public SVM() {
        _param = new svm_parameter();
        _param.svm_type = svm_parameter.C_SVC;
        _param.kernel_type = svm_parameter.RBF;
        _param.degree = 3;
        _param.gamma = 0.5;
        _param.coef0 = 0;
        _param.nu = 0.5;
        _param.cache_size = 40;
        _param.C = 0.3;
        _param.eps = 1e-3;
        _param.p = 0.1;
        _param.shrinking = 1;
        _param.probability = 0;
        _param.nr_weight = 0;
        _param.weight_label = new int[0];
        _param.weight = new double[0];
    }

    public svm_problem getProblem(List<List<Double>> values, List<Double> classes) {
        List<Double> vy = new ArrayList<Double>();
        List<svm_node[]> vx = new ArrayList<svm_node[]>();

        for (int i = 0; i < classes.size(); i++) {
            vy.add(classes.get(i));

            List<svm_node> nodes = new ArrayList<svm_node>();
            for (int j = 0; j < values.get(i).size(); j++) {
                svm_node node = new svm_node();
                node.index = j;
                node.value = values.get(i).get(j);
                nodes.add(node);
            }

            svm_node[] nds = new svm_node[nodes.size()];
            nodes.toArray(nds);
            vx.add(nds);
        }

        svm_node[][] dblCVx = new svm_node[vx.size()][];
        vx.toArray(dblCVx);

        double[] dblCVy = new double[vy.size()];
        for (int i = 0; i < vy.size(); i++) {
            dblCVy[i] = vy.get(i);
        }


        svm_problem prob = new svm_problem();
        prob.l = vy.size();
        prob.x = dblCVx;
        prob.y = dblCVy;

        return prob;
    }

    public void train(svm_problem prob) {
        _svm = svm.svm_train(prob, _param);

    }

    public double predict(List<Double> feature) {
        List<svm_node> nodes = new ArrayList<svm_node>();
        for (int j = 0; j < feature.size(); j++) {
            svm_node node = new svm_node();
            node.index = j;
            node.value = feature.get(j);
            nodes.add(node);
        }

        svm_node[] nds = new svm_node[nodes.size()];
        nodes.toArray(nds);

        return svm.svm_predict(_svm, nds);
    }
}
