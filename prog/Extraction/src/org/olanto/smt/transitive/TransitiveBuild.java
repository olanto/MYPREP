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
package org.olanto.smt.transitive;

import java.io.*;
import java.util.HashMap;

/**
 * copier les n premières lignes
 */
public class TransitiveBuild {

    static String root;
    static HashMap<String, String> mapyyzz;
    static final int INIT_MAP = 20000000; //(75% util)
    static final String soName = "corpus.so";
    static final String taName = "corpus.ta";
    static final String txt_encoding = "UTF-8";
    static final int limit = 20000000;

    public static void main(String[] args) {
        root = "C:/CORPUS/ONU/SMT";
        String xxyy = "FREN";
        String yyzz = "ENES";
        extract(root, xxyy, yyzz);

    }

    public static final void extract(String root, String xxyy, String yyzz) {
        String xxzz = xxyy.substring(0, 2) + yyzz.substring(2, 4);
        System.out.println("build : " + xxyy + "->" + yyzz + "=" + xxzz + " in:" + root);
        String SOxxyy = root + "/" + xxyy + "/" + soName;
        String TAxxyy = root + "/" + xxyy + "/" + taName;
        String SOyyzz = root + "/" + yyzz + "/" + soName;
        String TAyyzz = root + "/" + yyzz + "/" + taName;
        String SOxxzz = root + "/" + xxzz + "/" + soName;
        String TAxxzz = root + "/" + xxzz + "/" + taName;
        buildDictio(SOyyzz, TAyyzz);
        buildXXZZ(SOxxyy, TAxxyy, SOxxzz, TAxxzz);
    }

    public static final void buildXXZZ(String SOxxyy, String TAxxyy, String SOxxzz, String TAxxzz) {
        System.out.println("------------- build corpus: " + SOxxzz + ", " + TAxxzz);
        int totread = 0;
        int totbuild = 0;
        try {
            InputStreamReader isrso = new InputStreamReader(new FileInputStream(SOxxyy), txt_encoding);
            BufferedReader so = new BufferedReader(isrso);
            InputStreamReader isrta = new InputStreamReader(new FileInputStream(TAxxyy), txt_encoding);
            BufferedReader ta = new BufferedReader(isrta);
            BufferedWriter outso = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SOxxzz), txt_encoding));
            BufferedWriter outta = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TAxxzz), txt_encoding));
            String wso = so.readLine();
            String wta = ta.readLine();
            while (wso != null && totread < limit) {
                totread++;
                String transitive = mapyyzz.get(wta);
                if (transitive != null) {
                    totbuild++;
                    outso.append(wso + "\n");
                    outta.append(transitive + "\n");
                }
                wso = so.readLine();
                wta = ta.readLine();
            }
            so.close();
            ta.close();
            outso.close();
            outta.close();
            System.out.println("   read entries: " + totread + ", generate entries: " + totbuild);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void buildDictio(String SOyyzz, String TAyyzz) {
        System.out.println("------------- build sentence dictionary: " + SOyyzz + ", " + TAyyzz);
        mapyyzz = new HashMap(INIT_MAP);
        int totdictEntry = 0;
        try {
            InputStreamReader isrso = new InputStreamReader(new FileInputStream(SOyyzz), txt_encoding);
            BufferedReader so = new BufferedReader(isrso);
            InputStreamReader isrta = new InputStreamReader(new FileInputStream(TAyyzz), txt_encoding);
            BufferedReader ta = new BufferedReader(isrta);
            String wso = so.readLine();
            String wta = ta.readLine();
            while (wso != null && totdictEntry < limit) {
                totdictEntry++;
                mapyyzz.put(wso, wta);
                wso = so.readLine();
                wta = ta.readLine();
            }
            so.close();
            ta.close();
            System.out.println("   read entries: " + totdictEntry + ", keep entries: " + mapyyzz.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
