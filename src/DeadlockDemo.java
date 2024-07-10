import java.io.*;
import java.util.concurrent.TimeUnit;


public class DeadlockDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    // Simulate a process with buffered output
    ProcessBuilder builder = new ProcessBuilder("java", "-cp", "", "BufferedProcess");
    Process process = builder.start();

    System.out.println("Before waitFor");
    // int exitCode = process.waitFor();
    // System.out.println(exitCode);

    // Read output after the process has finished (problematic on Windows)
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
      if (process.waitFor() != 0) {
        throw new IOException("failed to print stream");
      }
    }

//        // Wait for process to finish

  }
}