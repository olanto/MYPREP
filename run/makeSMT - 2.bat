@echo on

java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 0 0 5 "C:/MYPREP" ENFR  > C:\MYPREP\logs\extraction_ENFR_logs.txt
java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 0 0 5 "C:/MYPREP" ENES  > C:\MYPREP\logs\extraction_ENES_logs.txt
java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 0 0 5 "C:/MYPREP" ENAR  > C:\MYPREP\logs\extraction_ENES_logs.txt
java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 0 0 5 "C:/MYPREP" ENRU  > C:\MYPREP\logs\extraction_ENES_logs.txt
java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 0 0 5 "C:/MYPREP" ENZH  > C:\MYPREP\logs\extraction_ENES_logs.txt
java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 0 0 5 "C:/MYPREP" ENDE  > C:\MYPREP\logs\extraction_ENES_logs.txt
@echo on