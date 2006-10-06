@echo Pre-Release...
@echo off
cmd /C mvn clean package site
@echo on
@echo Coping and Zipping...
@echo off
cmd /C ant
@echo on
@echo Release sucessfully, See <target> directory