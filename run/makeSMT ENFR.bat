@echo on

cd C:/MYPREP/run
c:



java -Xmx1000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.extraction.RUNExtraction 500 500 600 "C:/MYPREP" ENFR  > C:\MYPREP\logs\extraction_ENFR_logs.txt
reverseTraining ENFR FREN
@echo on