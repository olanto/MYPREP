#!/bin/bash

cp -r -f /home/olanto/MYPREP/SMT/$1/* /home/olanto/MYPREP/SMT/$2/
cd /home/olanto/MYPREP/SMT/$2/
mv corpus.so corpus.sota
mv tuning.so tuning.sota
mv eval.so eval.sota
mv corpus.ta corpus.so
mv tuning.ta tuning.so
mv eval.ta eval.so
mv corpus.sota corpus.ta
mv tuning.sota tuning.ta
mv eval.sota eval.ta


