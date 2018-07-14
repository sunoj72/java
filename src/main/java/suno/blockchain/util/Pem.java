package suno.blockchain.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class Pem {
  private PemObject pemObject;

  public Pem (Key key, String desc) {
    this.pemObject = new PemObject(desc, key.getEncoded());
  }

  public void write(String filename) throws FileNotFoundException, IOException {
    PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));
    try {
      pemWriter.writeObject(this.pemObject);
    } finally {
      pemWriter.close();
    }
  }
}