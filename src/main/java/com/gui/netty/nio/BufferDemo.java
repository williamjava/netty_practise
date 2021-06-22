package com.gui.netty.nio;

import java.nio.IntBuffer;

/**
 * @author whj
 *
 * 简单认识NIO中的Buffer(缓冲区)，可往里写入数据，也可从中读取数据，但是读写切换一定要使用方法flip()。
 */
public class BufferDemo {
    public static void main(String[] args) {
        //1.创建一个Buffer(容量为10)
        IntBuffer buffer = IntBuffer.allocate(10);

        //2.往buffer中写入数据，这里我们写入10个数字
        for (int i = 1; i <= buffer.capacity(); i++) {
            buffer.put(i * 6);
        }

        //如果放入更多的数据到buffer中会怎么样？抛出BufferOverflowException
        /*buffer.put(999);*/

        //3.从buffer中读取数据，注意，再开始读取之前，一定要先调用Buffer的flip方法
        buffer.flip();

        while (buffer.hasRemaining()) {
            //Buffer底层实际上也有一个数组，每get一次，下标属性position会加1
            System.out.println(buffer.get());
        }
    }
}
