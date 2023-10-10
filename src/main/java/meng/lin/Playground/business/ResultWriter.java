package meng.lin.Playground.business;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter implements AutoCloseable {

  private final String fileName;
  private final BufferedWriter writer;

  public ResultWriter(String fileName) throws IOException {
    this.fileName = fileName;
    writer = new BufferedWriter(new FileWriter(this.fileName, true));

  }

  public void write(String str) throws IOException {
    writer.write(str);
  }

  public void append(String str) throws IOException {
    writer.append(str);
  }

  @Override
  public void close() throws Exception {
    if (writer != null) {
      writer.close();
    }
  }
}
