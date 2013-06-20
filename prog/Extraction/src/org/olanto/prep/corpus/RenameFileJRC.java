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
 *  renommer les fichiers
 *  
 */
public class RenameFileJRC {

    static String DOCEXT, root, targetRoot;
    static int countreplace = 0;
     static final String header = "$$$AUT";
    static final String newHeader = "";
static final String SEPARATOR = "¦";
    
    
    public static void main(String[] args) {
        DOCEXT = ".txt";
        root = "C:/MYCAT/corpus/txt";
        targetRoot = root;
        System.out.println("start replacement for " + header);
        indexdir(root);
        System.out.println("countreplace: " + countreplace);

    }

    public static void indexdir(String path) {
        File f = new File(path);
        if (f.isFile()) {
                //System.out.println("file:" + f);
            if (path.endsWith(DOCEXT)) {
                String name=f.getName();
                if (name.startsWith("jrc")&&!name.startsWith("jrc¦")){
                    String newpath=path.replace("jrc", "jrc¦");
                   // System.out.println("new:" + newpath);
                    f.renameTo(new File(newpath));
                }
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

 
}
