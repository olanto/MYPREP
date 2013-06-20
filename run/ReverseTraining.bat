@echo on
set root=C:\MYPREP\SMT
xcopy /S /Y %root%\%1\*.so  %root%\%2\*.ta
xcopy /S /Y %root%\%1\*.ta  %root%\%2\*.so
