package com.gui.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author whj
 *
 * 文件拷贝-直接使用Channel的transferfrom方法
 */
public class FileChannelTransferFromDemo {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("beach1.jpg");
        FileChannel sourceChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("beach2.jpg");
        FileChannel destChannel = fileOutputStream.getChannel();

        destChannel.transferFrom(sourceChannel,0, sourceChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
        sourceChannel.close();
        destChannel.close();
    }
}
