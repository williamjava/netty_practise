package com.gui.netty.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author whj
 *
 * Buffer和Channel注意事项3
 *
 * MappedByteBuffer用于直接基于内存(堆外内存)修改数据，性能高
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        //指定要访问的文件并获取文件通道
        RandomAccessFile file = new RandomAccessFile("hello.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        //通过Channel获取MappedByteBuffer
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        //修改数据
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(1, (byte) 'E');

        file.close();
    }
}
