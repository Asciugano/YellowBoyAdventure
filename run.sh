#!/bin/bash

javac -d out $(find . -name "*.java")
cp -r res/* out/ 2>/dev/null || true

java -cp out Main.Main
