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
package org.olanto.smt.wrapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

/**
 *
 * pour transformer une MT au format XLS en deux fichiers distincts
 */
public class WrapperXLMT {

    static Pattern sep=Pattern.compile("[\\t]");
    
    public static void main(String[] args) {
        try {

            String SourceXL = "C:/CORPUS/corpus_20120214/Mémoires_de_traduction/EN_FR.txt";
            String targetEN ="C:/CORPUS/corpus_20120214/Mémoires_de_traduction/EN1.txt";
            String targetFR ="C:/CORPUS/corpus_20120214/Mémoires_de_traduction/FR1.txt";
            Extract(SourceXL, targetEN, targetFR);
            SourceXL = "C:/CORPUS/corpus_20120214/Mémoires_de_traduction/FR_EN.txt";
             targetEN ="C:/CORPUS/corpus_20120214/Mémoires_de_traduction/FR2.txt";
             targetFR ="C:/CORPUS/corpus_20120214/Mémoires_de_traduction/EN2.txt";
            Extract(SourceXL, targetEN, targetFR); } catch (Exception ex) {
            
            ex.printStackTrace();
        }
    }

    public static void Extract(String fileName, String targetSO, String targetTA) {
    
        try {
            System.out.println("open :" + fileName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            BufferedReader insource = new BufferedReader(isr);
            OutputStreamWriter outso = new OutputStreamWriter(new FileOutputStream(targetSO), "UTF-8");
           OutputStreamWriter outta = new OutputStreamWriter(new FileOutputStream(targetTA), "UTF-8");
      String w = insource.readLine();  // skip first line
      w = insource.readLine();
            while (w != null) {
                String[] col=sep.split(w); 
                if (col.length>6){
                outso.append(col[4]+"\n");
                outta.append(col[6]+"\n");
                }
                w = insource.readLine();            
            }
            isr.close();
              outso.close();
         outta.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
