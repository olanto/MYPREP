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
package org.olanto.comp.extraction;

import org.olanto.smt.extraction.*;


public class RUNExtraction {


    public static void main(String[] args) {
       if (args.length < 7) {
            String syntax = "Error int parameters:\nRunExtraction minterm minlen maxratio minscore root so ta" ;
            System.out.println(syntax);
            System.exit(0);
        }
        else {
            int minterm=Integer.parseInt(args[0]);
            int minlen=Integer.parseInt(args[1]);
            float maxratio=Float.parseFloat(args[2]);
            float minscore=Float.parseFloat(args[3]);
            String root=args[4];
            String so=args[5];
            String ta=args[6];
               ExtractAndFilterCorpus.extractAndFilter(minterm, minlen, maxratio, minscore, root, so, ta);
        }
    }


}





