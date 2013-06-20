#!/bin/bash

cd /home/olanto/MYPREP/dist

java -Xmx1000m -classpath "./alignement2.jar" org.olanto.zahir.run.bitext.AlignBITEXT_classic > /home/olanto/MYPREP/logs/makeTMX_logs.txt


