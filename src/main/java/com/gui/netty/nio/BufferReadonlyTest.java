package com.gui.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author whj
 *
 * Buffer和Channel注意事项2
 *
 * Buffer的只读，如果往一个只读的Buffer中put数据，会出现异常ReadOnlyBufferException
 */
public class BufferReadonlyTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(50);
        buffer.putInt(10);
        buffer.putInt(20);
        buffer.putInt(30);

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        readOnlyBuffer.putInt(40);
    }
}
