package com.gui.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author whj
 * <p>
 * Channel通过缓冲区写数据到文件中
 */
public class FileChannelWriteDemo {
    public static void main(String[] args) throws Exception{
        /**要写入到文件中的数据*/
        String msg = "Hello, FileChannel...";

        //1.创建一个输出流
        FileOutputStream stream = new FileOutputStream("E:\\hello.txt");

        //2.获取通道
        FileChannel fileChannel = stream.getChannel();

        //3.创建缓冲区并写入数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(msg.getBytes());

        //4.通道从缓冲区读取数据并写入到通道，但是在此之前缓冲区需要进行读写切换flip
        byteBuffer.flip();
        fileChannel.write(byteBuffer);

        //5.因为实际操作的还是流，需要进行关闭操作
        stream.close();
    }
}
