/**
 * ********
 * Copyright © 2010-2012 Olanto Foundation Geneva
 *
 * This file is part of myMT.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package org.olanto.smt.mancorpus;

import org.olanto.smt.transitive.*;
import java.io.*;
import java.util.HashMap;

/**
 * copier les n premières lignes
 */
public class BuildEvalTuning {

    static String root;
    static final String txt_encoding = "UTF-8";

    public static void main(String[] args) {
        root = "C:/CORPUS/OPUS/UN/SMT/ARFR";
        extract(root, 500, 500, 1000);

    }

    public static final void extract(String root, int maxTuning, int maxEval, int step) {
        String SO_orig = root + "/" + "orig.so";
        String TA_orig = root + "/" + "orig.ta";
        String SO_corpus = root + "/" + "corpus.so";
        String TA_corpus = root + "/" + "corpus.ta";
        String SO_eval = root + "/" + "eval.so";
        String TA_eval = root + "/" + "eval.ta";
        String SO_tuning = root + "/" + "tuning.so";
        String TA_tuning = root + "/" + "tuning.ta";
        System.out.println("------------- build corpus: " + SO_corpus + ", " + TA_corpus);
        System.out.println("------------- build eval: " + SO_eval + ", " + TA_eval);
        System.out.println("------------- build eval: " + SO_tuning + ", " + TA_tuning);
        int totread = 0;
        int collect = 0;
        int eval = 0;
        int tuning = 0;
        try {
            InputStreamReader isrso = new InputStreamReader(new FileInputStream(SO_orig), txt_encoding);
            BufferedReader so = new BufferedReader(isrso);
            InputStreamReader isrta = new InputStreamReader(new FileInputStream(TA_orig), txt_encoding);
            BufferedReader ta = new BufferedReader(isrta);
            BufferedWriter outcorpusso = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SO_corpus), txt_encoding));
            BufferedWriter outcorpusta = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TA_corpus), txt_encoding));
            BufferedWriter outevalso = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SO_eval), txt_encoding));
            BufferedWriter outevalta = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TA_eval), txt_encoding));
            BufferedWriter outtuningso = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SO_tuning), txt_encoding));
            BufferedWriter outtuningta = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TA_tuning), txt_encoding));
            String wso = so.readLine();
            String wta = ta.readLine();
            while (wso != null) {
                totread++;
                collect++;
                outcorpusso.append(wso + "\n");
                outcorpusta.append(wta + "\n");
                if (collect >= step) { // recopie
                    collect = 0;
                    if (eval < maxEval) { // recopie
                        eval++;
                        wso = so.readLine();
                        wta = ta.readLine();
                        outevalso.append(wso + "\n");
                        outevalta.append(wta + "\n");
                    } 
                    if (tuning < maxTuning) { // recopie
                        tuning++;
                        wso = so.readLine();
                        wta = ta.readLine();
                        outtuningso.append(wso + "\n");
                        outtuningta.append(wta + "\n");
                    }
                }
                    wso = so.readLine();
                    wta = ta.readLine();
                }
                so.close();
                ta.close();
                outcorpusso.close();
                outcorpusta.close();
                outevalso.close();
                outevalta.close();
                 outtuningso.close();
                outtuningta.close();
                System.out.println("   read entries: " + totread );
            }   catch (Exception e) {
            e.printStackTrace();
        }
    }

 
}