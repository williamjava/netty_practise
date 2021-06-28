package com.gui.netty.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author whj
 *
 * Buffer和Channel注意事项4
 *
 * 读写数据的时候，可以一次性使用多个缓冲区(分散和聚合)
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception{
        //1.创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.创建需要监听的端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //3.绑定端口到serversocket并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //4.创建缓冲区数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //5.等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        //6.循环读取
        //从客户端能读取的最大字符长度为8
        int maxMessageLen = 8;
        while (true) {
            long byteRead = 0;
            while (byteRead < maxMessageLen) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead=" + byteRead);
                Arrays.stream(byteBuffers).map(byteBuffer -> "position=" + byteBuffer.position() + ", limit=" + byteBuffer.limit()).forEach(System.out::println);
            }

            //数据回显
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            long byteWrite = 0;
            while (byteWrite < maxMessageLen) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            //Buffer复位(clear)
            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite + ", maxMessageLen=" + maxMessageLen);
        }
    }
}
