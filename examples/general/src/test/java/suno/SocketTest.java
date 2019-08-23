package suno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import socket.model.Message;
import socket.model.MessageEnum;
import socket.util.MessageBuilder;

public class SocketTest {
  @Test
  public void testEnum() {
    MessageEnum cmd1 = MessageEnum.COMMAND_1;
    MessageEnum cmd2 = MessageEnum.COMMAND_2;

    assertEquals("CMD1", cmd1.getCommandString());
    assertEquals("CMD2", cmd2.getCommandString());
  }

  @Test
  public void testEnum2() {
    MessageEnum cmd1 = MessageEnum.valueOf("COMMAND_1");
    MessageEnum cmd2 = MessageEnum.valueOf("COMMAND_2");

    assertEquals("Cmd1 Not Equals", MessageEnum.COMMAND_1, cmd1);
    assertEquals("Cmd2 Not Equals", MessageEnum.COMMAND_2, cmd2);
  }

  @Test
  public void testEnum3() {
    MessageEnum cmd1 = MessageEnum.getEnum("CMD1");
    MessageEnum cmd2 = MessageEnum.getEnum("CMD2");

    assertEquals("Cmd1 Not Equals", MessageEnum.COMMAND_1, cmd1);
    System.out.println(cmd1);
    assertEquals("Cmd2 Not Equals", MessageEnum.COMMAND_2, cmd2);
    System.out.println(cmd2);
  }

  @Test
  public void testMessageBuilder() {
    String buff = "CMD1#hello";
    Message msg = MessageBuilder.build(buff);

    assertNotNull(msg);
    assertEquals("Message Not Equals", MessageEnum.COMMAND_1, msg.getCommand());
    System.out.println(msg);

    buff = "CMD2";
    msg = MessageBuilder.build(buff);
    assertNotNull(msg);
    assertEquals("Cmd1 Not Equals", MessageEnum.COMMAND_2, msg.getCommand());
    System.out.println(msg);

    buff = "CMD3";
    msg = MessageBuilder.build(buff);
    assertNull(msg);

  }

}