package org.throwable.serializer.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.throwable.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author throwable
 * @version 1.0
 * @since 2017/2/24 14:29
 */
public class KryoSerializer implements Serializer {


    @Override
    public byte[] serialize(Object obj) {
        Kryo kryo = new Kryo();
        ByteArrayOutputStream byteArrayOutputStream = null;
        Output output = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            output = new Output(byteArrayOutputStream);
            kryo.writeClassAndObject(output, obj);
            return byteArrayOutputStream.toByteArray();
        } finally {
            if (null != output) {
                output.close();
            }
            if (null != byteArrayOutputStream) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] bytes) {
        Kryo kryo = new Kryo();
        ByteArrayInputStream byteArrayInputStream = null;
        Input input = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            input = new Input(byteArrayInputStream);
            return (T) kryo.readClassAndObject(input);
        } finally {
            if (null != input) {
                input.close();
            }
            if (null != byteArrayInputStream) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }
}
