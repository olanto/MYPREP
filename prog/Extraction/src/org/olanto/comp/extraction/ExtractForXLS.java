/**********
Copyright © 2010-2013 Olanto Foundation Geneva

This file is part of myMT.

myCAT is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

myCAT is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

 **********/
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

/**wiki
 *
 * @author jg
 * 
 * ce programme fait une extraction pour XLS
 */
public class ExtractForXLS {

    private static OutputStreamWriter outxls;
    private static int tot;
    private static String roottmx, so, ta;
    private static String codingwrite = "ISO-8859-1";// "ISO-8859-1"; "UTF-8";
    private static String codingread = "UTF-8"; // "ISO-8859-1";"UTF-8";
    private static String finalcorpus;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            so = "en";
            ta = "fr";
            roottmx = "C:/WIKIJG/tmx/" + so + "-" + ta + "/";
            finalcorpus = "C:/WIKIJG/final/" + so + "-" + ta + "/";
            outxls = new OutputStreamWriter(new FileOutputStream(finalcorpus + "corpus.csv"), codingwrite);
            indexdir(roottmx);
            outxls.flush();
            outxls.close();
        } catch (Exception ex) {
            Logger.getLogger(ExtractForXLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tot:" + tot);
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

                float ratio = (float) lenso / (float) lenta;
                boolean flip = false;
                int minlen=lenso;
                if (ratio < 1) {
                    flip = true;
                    minlen=lenta;
                    ratio = 1 / ratio;
                }

                outxls.append(""+(int)(score*(float)100) + ";" + ident + ";" + noident + ";" + lenso + ";" + lenta + ";" + (int)ratio*10 + ";" + minlen + ";" + flip + "\n");

                w = in.readLine();
            }
            isr.close();
            in.close();
            isr = null;
            in = null;
        } catch (Exception ex) {
            Logger.getLogger(ExtractForXLS.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
