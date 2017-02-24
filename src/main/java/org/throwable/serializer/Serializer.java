package org.throwable.serializer;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/2/24 14:26
 */
public interface Serializer {


    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes);

}
