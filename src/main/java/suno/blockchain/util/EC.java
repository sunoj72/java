package suno.blockchain.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class EC {
  private final String ALGORITHM = "sect163k1";

  public void generate(String privateKeyName, String publicKeyName) throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
    ECGenParameterSpec ecsp = new ECGenParameterSpec(ALGORITHM);

    generator.initialize(ecsp, new SecureRandom());

    KeyPair kp = generator.generateKeyPair();
    System.out.println("One key pair was created.");

    PrivateKey priv = kp.getPrivate();
    PublicKey pub = kp.getPublic();

    writePemFile(priv, "EC PRIVATE KEY", privateKeyName);
    writePemFile(pub, "EC PUBLIC KEY", publicKeyName);
  }

  private void writePemFile(Key key, String desc, String filename) throws FileNotFoundException, IOException {
    Pem pemFile = new Pem(key, desc);
    pemFile.write(filename);
    System.out.println(String.format("EC 암호키 %s을(를) %s 파일로 내보냈습니다.", desc, filename));
  }

  public PrivateKey readPrivateKeyFromFile(String privateKeyFile)
    throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

    String data = readString(privateKeyFile);
    System.out.println("EC 개인키를 " + privateKeyFile + "로부터 불러왔습니다.");
    System.out.println(data);

    data = data.replace("-----BEGIN EC PRIVATE KEY-----", "");
    data = data.replace("-----END EC PRIVATE KEY-----", "");

    byte[] decoded = Base64.decode(data);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
    KeyFactory factory = KeyFactory.getInstance("ECDSA");
    PrivateKey key = factory.generatePrivate(spec);

    return key;
  }

  public PublicKey readPublicKeyFromFile(String publicKeyFile)
    throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

    String data = readString(publicKeyFile);
    System.out.println("EC 공개키를 " + publicKeyFile + "로부터 불러왔습니다.");
    System.out.println(data);

    data = data.replace("-----BEGIN EC PUBLIC KEY-----", "");
    data = data.replace("-----END EC PUBLIC KEY-----", "");

    byte[] decoded = Base64.decode(data);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
    KeyFactory factory = KeyFactory.getInstance("ECDSA");
    PublicKey key = factory.generatePublic(spec);

    return key;
  }

  public String readString(String filename)
    throws FileNotFoundException, IOException {

    String pem = "", line;
    BufferedReader br = new BufferedReader(new FileReader(filename));

    while ((line = br.readLine()) != null) {
      pem += line + "\n";
    }
    br.close();

    return pem;
  }
}