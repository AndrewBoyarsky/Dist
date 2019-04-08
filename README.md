*How to run applications?*
1. Install jdk from [official site](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html), if not installed. Version 8+ required.
2. Go to src folder
3. Choose one among available classes:
  * RaceConditionSynchronized.java
  * RaceConditionSemaphore.java
  * RaceConditionAtomic.java
  * RaceCondition.java
  * JavaServerSocket.java
  * JavaPipes.java
  * JavaOneServerSocket.java
  * JavaOneClientSocket.java
  * JavaClientSocket.java
4. Compile it using `javac` command  For example: `javac JavaPipes.java` 
5. Run it using `java` command. For example, `java JavaOneServerSocket ` + `java JavaOneClientSocket`