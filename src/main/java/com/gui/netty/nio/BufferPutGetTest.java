package com.gui.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author whj
 *
 * Buffer和Channel注意事项1
 *
 * Buffer的put和get操作，对应的数据类型需要一直，否则会出现异常：BufferUnderflowException
 * 注意：如果get和put的类型不一致，也不一定会出现异常，但是读取出来的数据不是对应位置put的数据！
 */
public class BufferPutGetTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putInt(66);
        buffer.putLong(100L);
        buffer.putDouble(166.66D);

        buffer.flip();

        /**
         * 正确的buffer读取顺序
         */
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());

        /**
         * 错误的buffer读取顺序
         */
        //System.out.println(buffer.getDouble());
        //System.out.println(buffer.getLong());
        //System.out.println(buffer.getDouble());
    }
}
