echo Pre-Release...
cmd /C mvn clean install site

echo Coping and Zipping...
cmd /C ant

echo Release sucessfully, See <target> directory