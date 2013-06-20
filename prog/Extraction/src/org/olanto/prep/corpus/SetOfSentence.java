/**********
Copyright © 2010-2012 Olanto Foundation Geneva

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

 **********/package org.olanto.prep.corpus;

import java.io.*;
import java.util.HashMap;

/**
 *
 * @author jg
 */
public class SetOfSentence {

    static int totset;
    public HashMap<String, Integer> hsub = new HashMap<>(100);

    public SetOfSentence(String sourceSub, int minlen) {
        try {

            processAFile(sourceSub, minlen);
            System.out.println("tot retained sentences:" + totset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processAFile(String fileName, int minlen) {

        try {
            System.out.println("open :" + fileName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader insource = new BufferedReader(isr);
            String w = insource.readLine();
            while (w != null) {
                if (w.length() < minlen) {
                    System.out.println(" exclude: |" + w + "|");
                } else {
                    hsub.put(w, totset);
                    totset++;
                }
                w = insource.readLine();
            }
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getid(String s) {
        return hsub.get(s);
    }
}
