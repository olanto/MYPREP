@echo on

java -Xmx10000m -classpath "C:\MYPREP\dist\Extraction.jar" org.olanto.smt.transitive.RUNTransitiveBuild "C:/CORPUS/ONU/SMT/" FREN ENES > C:\MYPREP\logs\transitive_FRES_logs.txt

@echo on