#!bin/bash

# javac -cp .:Hospital/necessary/mysql-connector-j-9.3.0.jar Hospital/necessary/Connector.java Hospital/main/Main.java 

# recompile all the .java files
find . -name "*.java" > sources.txt
javac @sources.txt

# javac -cp .:Hospital/necessary/mysql-connector-j-9.3.0.jar Hospital/necessary/database/PatientTable.java Hospital/necessary/interaction/patient.java Hospital/main/Main.java

java -cp .:Hospital/necessary/mysql-connector-j-9.3.0.jar Hospital.main.Main