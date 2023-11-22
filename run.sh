javac *.java
jar cvfm TicTacToe.jar manifest.txt *.class
rm -v *.class
java -jar TicTacToe.jar