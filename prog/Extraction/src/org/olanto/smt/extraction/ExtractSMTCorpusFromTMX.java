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
package org.olanto.smt.extraction;

import java.io.*;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.*;
import org.jdom.input.*;
import java.util.List;
import java.util.Iterator;

/**
- version qui améliore la détection des phrases non-traduites
- qui limite les phrases trop courtes
 */
public class ExtractSMTCorpusFromTMX {

    static org.jdom.Document document;
    static Element racine;
    static int idSeq = 0;
    static int badFiles = 0;
    static int countOK = 0;
    static int totPhrase = 0;
    static int totUpper = 0;
    static int totNoTrans = 0;
    static int totNoTransPatialMatch = 0;
    static int totTooSmall = 0;
    static int totPointVirgule = 0;
    static String TMXFolder, EXT, pathOUT, langSRC, langTAR;
    static OutputStreamWriter outSRC, outTAR;
    static OutputStreamWriter tuningSRC, tuningTAR;
    static OutputStreamWriter evalSRC, evalTAR;
    static boolean eval = false;
    static int step = 400;
    static int countTuning = 0;
    static int countEval = 0;
    static int maxTuning = 1000;
    static int maxEval = 1000;
    static int limitSize = 15;

    public static void extract(String SOTA, int _maxTuning, int _maxEval, int _step, String root) {
        try {
            System.out.println();
            System.out.println("______________________________________________________________");
            System.out.println("Corpus " + SOTA);
            System.out.println("root: " + root);
            System.out.println("_maxTuning " + _maxTuning);
            System.out.println("_maxEval " + _maxEval);
            System.out.println("_step " + _step);
            System.out.println();

            idSeq = 0;
            countOK = 0;
            totPhrase = 0;
            totUpper = 0;
            totNoTrans = 0;
            totNoTransPatialMatch = 0;
            totTooSmall = 0;
            totPointVirgule = 0;
            maxTuning = _maxTuning;
            maxEval = _maxEval;
            step = _step;
            langSRC = "so";
            langTAR = "ta";
            TMXFolder = root + "/TMX/" + SOTA;
            pathOUT = root + "/SMT/" + SOTA;
            EXT = ".tmx";
            outSRC = new OutputStreamWriter(new FileOutputStream(pathOUT + "/corpus." + langSRC), "UTF-8");
            outTAR = new OutputStreamWriter(new FileOutputStream(pathOUT + "/corpus." + langTAR), "UTF-8");
            tuningSRC = new OutputStreamWriter(new FileOutputStream(pathOUT + "/tuning." + langSRC), "UTF-8");
            tuningTAR = new OutputStreamWriter(new FileOutputStream(pathOUT + "/tuning." + langTAR), "UTF-8");
            evalSRC = new OutputStreamWriter(new FileOutputStream(pathOUT + "/eval." + langSRC), "UTF-8");
            evalTAR = new OutputStreamWriter(new FileOutputStream(pathOUT + "/eval." + langTAR), "UTF-8");
            indexdir(TMXFolder);
            System.out.println("tot files:" + idSeq);
            System.out.println("tot  bad files:" + badFiles);
            System.out.println("tot phrases:" + totPhrase);
            System.out.println("tot Upper:" + totUpper);
            System.out.println("tot No Translation:" + totNoTrans);
            System.out.println("tot No Translation partial match:" + totNoTransPatialMatch);
            System.out.println("tot too small:" + totTooSmall);
            System.out.println("tot contains ';' :" + totPointVirgule);

            outSRC.close();
            outTAR.close();
            tuningSRC.close();
            tuningTAR.close();
            evalSRC.close();
            evalTAR.close();
        } catch (Exception ex) {
            System.out.println("IO error");
        }

    }

    public static void indexdir(String path) {
        File f = new File(path);
        if (f.isFile()) {
            //System.out.println("path:"+f);
            if (path.endsWith(EXT)) {
                indexdoc(path, f.getName());
            }
        } else {
            String[] lf = f.list();
            int ilf = Array.getLength(lf);
            for (int i = 0; i < ilf; i++) {
                indexdir(path + "/" + lf[i]);
            }
        }
    }

    public static void indexdoc(String path, String name) {
        //On crée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        try {
            sxb.setValidation(false);
            String xml = skipHeader(path);
            document = sxb.build(new StringReader(xml));
        } catch (Exception e) {
            System.out.println("error in file:" + path);
            e.printStackTrace();
            badFiles++;
        }
        racine = document.getRootElement();
        afficheALL();
        idSeq++;
    }

    public static String skipHeader(String path) {
        StringBuffer res = new StringBuffer();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader in = new BufferedReader(isr);
            String w;
            w = in.readLine();

            int count = 0;
            while (w != null) {
                if (count >= 2) {
                    res.append(w + "\n");
                }
                count++;
                w = in.readLine();
            }
            in.close();
            isr.close();

        } catch (Exception e) {
            System.err.println("IO error during read :");
            e.printStackTrace();
        }
        return res.toString();
    }

    static String getDoc(Element e) {

        boolean localverbose = false;
        if (localverbose) {
            System.out.println(e.getName());      //On affiche le nom de l'element courant
        }
        if (localverbose) {
            System.out.println(e.getText());     //On affiche le texte
        }
        getInfo(e);
        return "";
    }

    static String getAtt(Element e, String att, boolean localverbose) {
        String val = e.getAttributeValue(att);
        if (localverbose) {
            System.out.println(att + ":" + val);
        }
        return val;
    }

    static String getSegText(Element e, boolean localverbose) {
        String val = e.getChildText("seg");
        if (localverbose) {
            System.out.println("--> " + val);
        }
        return val;
    }

    static void getInfo(Element e) {
        try {
            boolean localverbose = false;
            if (localverbose) {
                System.out.println(e.getName());
            }
            List listNode = e.getChildren();
            String src = getSegText((Element) listNode.get(0), false);
            String tar = getSegText((Element) listNode.get(1), false);
            boolean ok = true;
            if (src.toUpperCase().equals(src)
               //     || ((tar.toUpperCase().equals(tar) & langTAR != "ar"))
                    ) {
                ok = false;
                totUpper++;
            }
            if (ok && src.equals(tar)) {
                //System.out.println(src);
                ok = false;
                totNoTrans++;
            }
            if (ok && (src.contains(tar) || tar.contains(src))) {
                //System.out.println(src+" <-> "+tar);
                ok = false;
                totNoTransPatialMatch++;

            }
            if (ok && (src.length() < limitSize || tar.length() < limitSize)) {
                //System.out.println(src+" <-> "+tar);
                ok = false;
                totTooSmall++;
            }

            if (ok && (src.contains(";") && tar.contains(";"))) {
                //System.out.println(src+" <-> "+tar);
                ok = true;
                totPointVirgule++;
            }

            if (ok) {
                countOK++;
                src=src.replace("|", "//");
                tar=tar.replace("|", "//");
                boolean writeOK = true;
                if (eval) {
                    eval = false;
                    if (countEval < maxEval) {
                        countEval++;
                        evalSRC.write(src + "\n");
                        evalTAR.write(tar + "\n");
                        writeOK = false;
                    }
                }
                if (countOK % step == 0) {
                    eval = true;
                    if (countTuning < maxTuning) {
                        countTuning++;
                        tuningSRC.write(src + "\n");
                        tuningTAR.write(tar + "\n");
                        writeOK = false;
                    }
                }
                if (writeOK) {
                    outSRC.write(src + "\n");
                    outTAR.write(tar + "\n");
                    totPhrase++;
                }
            }
            //System.out.println(src+"-"+tar);
        } catch (IOException ex) {
            Logger.getLogger(ExtractSMTCorpusFromTMX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void afficheALL() {
        //System.out.println("racine");
        List listNode0 = racine.getChildren();
        //System.out.println(((Element) listNode0.get(1)).getName());
        //On crée un Iterator sur notre liste

        List listNode = ((Element) listNode0.get(1)).getChildren();  // body

        Iterator i = listNode.iterator();
        int count = 0;
        while (i.hasNext()) {
            //On recrée l'Element courant � chaque tour de boucle afin de
            //pouvoir utiliser les m�thodes propres aux Element comme :
            //selectionner un noeud fils, modifier du texte, etc...
            Element courant = (Element) i.next();
            getDoc(courant);
            count++;
        }
        //System.out.println("end racine count:" + count);

    }
}
