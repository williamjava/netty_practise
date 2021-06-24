package com.gui.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author whj
 * <p>
 * 通过Channel从文件中读取数据并输出到控制台
 */
public class FileChannelReadDemo {
    public static void main(String[] args) throws Exception{
        //1.指定要被读取的数据文件
        File file = new File("E:\\hello.txt");

        //2.创建输入流
        FileInputStream fileInputStream = new FileInputStream(file);

        //3.根据流获得通道
        FileChannel channel = fileInputStream.getChannel();

        //4.创建缓冲区ByteBuffer并写入数据(这里是写Buffer)
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);

        //5.输出缓冲区数据到控制台(这里是读Buffer)，注意一定要flip
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
