/**
 * ********
 * Copyright © 2010-2013 Olanto Foundation Geneva
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
package org.olanto.comp.extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Extrait et filtre le résultat des alignements de textes comparables
 *
 *
 */
public class ExtractAndFilterCorpus {

    private static OutputStreamWriter outso, outta;
    private static int tot, totaccepted;
    private static int tottoosmall, totbadscore, tottoodifferent, notenoughterm;
    private static String roottmx, so, ta;
    private static String codingwrite = "UTF-8";// "ISO-8859-1"; "UTF-8";
    private static String codingread = "UTF-8"; // "ISO-8859-1";"UTF-8";
    private static String finalcorpus;
    private  static int MINLEN = 0;
    private  static int MINTERM = 2;
    private  static float MAXRATIO = 1.7f;
    private  static float MINSCORE = 0.3f;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        so = "EN";
        ta = "FR";

        extractAndFilter(8, 4, 1.7f, 0.3f, "C:/MYPREP", so, ta);
    }

    public static void extractAndFilter(int _MINTERM, int _MINLEN, float _MAXRATIO, float _MINSCORE, String root, String _so, String _ta) {
        try {
            so = _so;
            ta = _ta;
            MINTERM = _MINTERM;
            MINLEN = _MINLEN;
            MAXRATIO = _MAXRATIO;
            MINSCORE = _MINSCORE;
            roottmx = root + "/COMP/" + so + ta + "/";
            finalcorpus = root + "/COMP/SMT/" + so + ta + "/";
            outso = new OutputStreamWriter(new FileOutputStream(finalcorpus + "corpus.so"), codingwrite);
            outta = new OutputStreamWriter(new FileOutputStream(finalcorpus + "corpus.ta"), codingwrite);
            indexdir(roottmx);
            outso.flush();
            outso.close();
            outta.flush();
            outta.close();
        } catch (Exception ex) {
            Logger.getLogger(ExtractAndFilterCorpus.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tot:" + tot + " ok:" + totaccepted);
        System.out.println("totbadscore:" + totbadscore);
        System.out.println("tottoosmall:" + tottoosmall);
        System.out.println("notenoughterm:" + notenoughterm);
        System.out.println("tottoodifferent:" + tottoodifferent);
    }

    public static boolean accepted(float score, int ident, int noident, int lenso, int lenta, String txtso, String txtta) {
        // not too small
        if (score < MINSCORE) {
            totbadscore++;
            //System.out.println("bad score "+"score:" + score  +" "+txtso+" <<>> "+txtta);
            return false;
        }
        // not too small
        if (lenso < MINLEN || lenta < MINLEN) {
            tottoosmall++;
            //System.out.println("too small "+"lenso:" + lenso + " lenta:" + lenta+" "+txtso+" <<>> "+txtta);
            return false;
        }
        if (noident < MINTERM) {
            notenoughterm++;
            //System.out.println("too small "+"lenso:" + lenso + " lenta:" + lenta+" "+txtso+" <<>> "+txtta);
            return false;
        }
        // not too different
        float ratio = (float) lenso / (float) lenta;
        if (ratio < 1) {
            ratio = 1 / ratio;
        }
        if (ratio > MAXRATIO) {
            tottoodifferent++;
            //System.out.println("too different "+"ratio:" + ratio +" lenso:" + lenso + " lenta:" + lenta+" "+txtso+" <<>> "+txtta);
            return false;
        }
        return true;
    }

    public static void indexdir(String path) {
        File f = new File(path);

        if (f.isFile()) {
            //System.out.println("path:"+f);
            if (path.endsWith("txt")) {
                process(path);
            }
        } else {
            String[] lf = f.list();
            int ilf = Array.getLength(lf);
            for (int i = 0; i < ilf; i++) {
                indexdir(path + "/" + lf[i]);
            }
        }
    }

    public static void process(String url) {
        open(url);
    }

    private static void open(String fname) {
        InputStreamReader isr = null;
        BufferedReader in = null;
        String w = null;
        try {
            isr = new InputStreamReader(new FileInputStream(fname), codingread);
            in = new BufferedReader(isr);

            w = in.readLine();
            while (w != null) {
                tot++;
                // score ident noident lenso lenta txtso txtta
                //System.out.println(w);
                String[] res = w.split("\\t");
                float score = Float.valueOf(res[0]);
                int ident = Integer.valueOf(res[1]);
                int noident = Integer.valueOf(res[2]);
                int lenso = Integer.valueOf(res[3]);
                int lenta = Integer.valueOf(res[4]);
                if (accepted(score, ident, noident, lenso, lenta, res[5], res[6])) {
                    totaccepted++;
                    outso.append(res[5] + "\n");
                    outta.append(res[6] + "\n");
                }
                w = in.readLine();
            }
            isr.close();
            in.close();
            isr = null;
            in = null;
        } catch (Exception ex) {
            Logger.getLogger(ExtractAndFilterCorpus.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
