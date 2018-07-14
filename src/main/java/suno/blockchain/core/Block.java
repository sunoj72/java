package suno.blockchain.core;

import suno.blockchain.util.Util;

public class Block {
  private int nonce;
  private int blockID;
  private String data;

  public Block(int blockID, int nonce, String data) {
    this.blockID = blockID;
    this.nonce = nonce;
    this.data = data;
  }

  /**
   * @return the blockID
   */
  public int getBlockID() {
    return blockID;
  }

  /**
   * @param blockID the blockID to set
   */
  public void setBlockID(int blockID) {
    this.blockID = blockID;
  }

  /**
   * @return the nonce
   */
  public int getNonce() {
    return nonce;
  }

  /**
   * @param nonce the nonce to set
   */
  public void setNonce(int nonce) {
    this.nonce = nonce;
  }

  /**
   * @return the data
   */
  public String getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(String data) {
    this.data = data;
  }

  public String getBlockHash() {
    return Util.getHash(nonce + data);
  }

  public void mine() {
    while(true) {
      if(getBlockHash().substring(0, 4).equals("0000")) {
        System.out.println(blockID + "번째 블록의 채굴에 성공했습니다.");
        break;
      }
  
      nonce++;
    }
  }

  public void getInformation() {
    System.out.println("--------------------------------------");
    System.out.println("블록 번호: " + getBlockID());
    System.out.println("채굴 변수 값: " + getNonce());
    System.out.println("블록 데이터: " + getData());
    System.out.println("블록 해시: " + getBlockHash());
    System.out.println("--------------------------------------");
  }
}