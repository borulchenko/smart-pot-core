package com.rborulchenko.spcore.mqtt;

/**
 * Enum to configure QOS level that are responcible for message delivery.
 * Refer to the connection between a broker and a client.
 *
 *   Once (not guaranteed) Use this option when message loss is acceptable, as it does not require any kind of
 * acknowledgment or persistence.
 *   At Least Once (guaranteed) Use this option when message loss is not acceptable and your subscribers can handle
 * duplicates.
 *   Only Once (guaranteed) Use this option when message loss is not acceptable and your subscribers cannot handle
 * duplicates.
 */
public enum QOS
{
  ONCE(0),
  AT_LEAST_ONCE(1),
  ONLY_ONCE(2);

  private final int levelCode;

  QOS(int levelCode) {
    this.levelCode = levelCode;
  }

  public int getLevelValue() {
    return this.levelCode;
  }
}
