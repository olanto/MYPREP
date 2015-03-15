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
 *  remplacement d'une chaine dans tous les fichiers d'un folder
 *  
 */
public class FindCharInCorpus {

    static String DOCEXT, root, targetRoot;
    static int countHtml = 0;
    static int countReplace = 0;
    static int countLine = 0;
    static final String oldStr = "|";
    static final String newStr = "/";

    public static void main(String[] args) {
        DOCEXT = ".txt";
        root = "C:/CORPUS/ENFR/corpus.so";
        countHtml = 0;
        targetRoot = root;
        file2String(root,"UTF-8");
        System.out.println("countLine:"+countLine);
        System.out.println("countReplace:"+countReplace);
  
    }

    public static void indexdir(String path) {
        File f = new File(path);
        if (f.isFile()) {
            //    System.out.println("file:" + f);
            if (path.endsWith(DOCEXT)) {
                indexdoc(path, f.getName());
            }
        } else {
            String[] lf = f.list();
            int ilf = Array.getLength(lf);
            System.out.println("folder:" + f);
            for (int i = 0; i < ilf; i++) {
                indexdir(path + "/" + lf[i]);
            }
        }
    }

    public static void indexdoc(String f, String name) {
        //System.out.println("process:" + f);
        String newContent = file2String(f, "UTF-8");
        if (newContent != null) {
            //copy(newContent, f, "UTF-8");
            //countHtml++;
        }
        if (countHtml % 1000 == 0) {
            System.out.println(countHtml);
        }
    }

    public static final String file2String(String path, String txt_encoding) {
        StringBuffer txt = new StringBuffer("");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), txt_encoding);
            BufferedReader in = new BufferedReader(isr);
            String w = in.readLine();
                while (w != null) {
                   if (w.contains("|")){
                       System.out.println(w);
                       countReplace++;
                   }                  
                    countLine++;
                    w = in.readLine();
                }
                in.close();
                String original = txt.toString();
                return original;
         } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(String s, String target, String targetEncoding) {
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), targetEncoding));
            output.append(s);
            output.close();
            output = null;
        } catch (Exception ex) {
            Logger.getLogger(FindCharInCorpus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
