import java.util.concurrent.TimeUnit;

public class BufferedProcess {
  public static void main(String[] args) {
    // Simulate a large amount of output (without flushing)
    StringBuilder output = new StringBuilder();
    for (int i = 1; i <= 10000; i++) {
      output.append("Output: ").append(i).append("\n");
    }
    System.out.print(output); // Print all at once

    try {
      TimeUnit.SECONDS.sleep(5); // Keep the process alive for a while
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}