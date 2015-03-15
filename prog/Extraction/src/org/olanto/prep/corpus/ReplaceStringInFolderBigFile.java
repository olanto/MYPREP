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
public class ReplaceStringInFolderBigFile {

    static String DOCEXT, root;
    static int countLine = 0;
    static final String oldStr = "|";
    static final String newStr = "/";

    public static void main(String[] args) {
        DOCEXT = ".so";
        root = "C:/CORPUS/ENES";
         System.out.println("start replacement for " + oldStr);
        indexdir(root);
  
    }

    public static void indexdir(String path) {
        File f = new File(path);
        if (f.isFile()) {
            //    System.out.println("file:" + f);
            if (path.endsWith(DOCEXT)) {
                processAFile(path,path+".new");
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

 
 
        public static void processAFile(String corpusSO, String corpusSOCOPY) {

        try {
            
        countLine=0;
            System.out.println("open :" + corpusSO);
            BufferedReader inSO = new BufferedReader(new InputStreamReader(new FileInputStream(corpusSO)));
            OutputStreamWriter outSO = new OutputStreamWriter(new FileOutputStream(corpusSOCOPY), "UTF-8");
             String wSO = inSO.readLine();  // tous le temps ensemble pour garder la synchro
            while (wSO != null) {
                countLine++;
                    outSO.append(wSO.replace(oldStr, newStr) + "\n");
                   wSO = inSO.readLine();
             }
            inSO.close();
            outSO.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("tot:"+countLine);
    }
    
}
