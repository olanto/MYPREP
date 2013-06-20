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
package org.olanto.smt.wrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.regex.Pattern;

/**
 *
 * pour ajouter coller des fichier .so .ta
 */
public class CollectTXT {

    static Pattern sep = Pattern.compile("[\\t]");
    static OutputStreamWriter outso;

    public static void main(String[] args) {
        OutputStreamWriter outso = null;
        try {
            String PATH = "C:/PREP/CONVERTED";
            String targetSO = "C:/CORPUS/corpus_20120214/Mémoires_de_traduction/lm.so";
            outso = new OutputStreamWriter(new FileOutputStream(targetSO), "UTF-8");
            indexdir(PATH);
            outso.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void indexdir(String path) {
        File f = new File(path);
        if (f.isFile()) {
            //System.out.println("path:" + f);
            if (path.endsWith(".txt")) {
                Extract(path);
            }
        } else {
            String[] lf = f.list();
            int ilf = Array.getLength(lf);
            for (int i = 0; i < ilf; i++) {
                indexdir(path + "/" + lf[i]);
            }
        }
    }

    public static void Extract(String fileName) {
        String w = "";
        try {
            System.out.println("open :" + fileName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            BufferedReader insource = new BufferedReader(isr);
            w = insource.readLine();
            while (w != null) {
                outso.append(w + "\n");
                w = insource.readLine();
            }
            isr.close();

        } catch (Exception e) {
            System.out.println("error:" + w);
            e.printStackTrace();
        }
    }
}
