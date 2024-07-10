# Sample to troubleshoot deadlock issue in Windows with waitFor()

## Calling getInputStream() before waitFor() on Linux machine
1) Run `cd src`
2) Compile classes by calling `javac DeadlockDemo.java BufferedProcess.java`
3) Run DeadlockDemo by calling `java DeadlockDemo`. Notice that output lines being printed:
```
. . .
Output line 9989
Output line 9990
Output line 9991
Output line 9992
Output line 9993
Output line 9994
Output line 9995
Output line 9996
Output line 9997
Output line 9998
Output line 9999
Output line 10000

```

## Calling getInputStream() before waitFor() on Windows machine
1) Repeat same steps from above. Notice same result. 

## Calling waitFor() first on Windows
1) Move the call to process waitFor() before getInputStream() as shown below:
```
 if (process.waitFor() != 0){
              throw new IOException("failed to print stream");
 }
  // Read output after the process has finished (problematic on Windows)
 try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
   String line;
   while ((line = reader.readLine()) != null) {
     System.out.println(line);
   }
 } 
```
2) Call `javac DeadlockDemo.java` to re-compile the class.
3) Run `java DeadlockDemo`. Program hangs.

This is likely caused due to the input stream not being returned promptly. 
From https://docs.oracle.com/javase/8/docs/api/java/lang/Process.html: "Because 
some native platforms only provide limited buffer size for standard input and output streams, 
failure to promptly write the input stream or read the output stream of the subprocess 
may cause the subprocess to block, or even deadlock."