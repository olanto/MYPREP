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

 **********/
package org.olanto.prep.corpus;

import java.io.*;
import java.lang.reflect.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  copier les n premières lignes
 */
public class CopyOnlyNFirstLines {

    static String  root;
     static int countLine = 0;
    public static void main(String[] args) {
        root = "C:/CORPUS/ONU/SMT/ENFR/corpus.so";
        System.out.println("start replacement for " + root);
        truncateFile(root, "UTF-8", 4000000);
         System.out.println("tot Lines  " + countLine);

    }

 
    public static final String truncateFile(String path, String txt_encoding, int limit) {
        StringBuffer txt = new StringBuffer("");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), txt_encoding);
            BufferedReader in = new BufferedReader(isr);
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+".reduce"), txt_encoding));
             String w = in.readLine();
                while (w != null && countLine<limit) {
                    output.append(w+"\n");
                    countLine++;
                    w = in.readLine();
                }
                in.close();output.close();
            
                String original = txt.toString();
                return original;
         } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
