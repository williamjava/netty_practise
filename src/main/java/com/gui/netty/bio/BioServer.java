package com.gui.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java原始io-服务端
 *
 * I/O模型——BIO，同步阻塞型，每一个客户端连接，服务端都会开一个线程处理。
 *
 * 这里仅仅创建一个服务端，客户端我们通过telnet连接：telnet 127.0.0.1 8888；Ctrl+]；send 消息；
 *
 * @author whj
 */
public class BioServer {
    public static void main(String[] args) throws Exception{
        //1.创建一个ServerSocket，监听8888端口
        ServerSocket serverSocket = new ServerSocket(8888);

        //2.通过线程池来管理线程
        ExecutorService threadPool = Executors.newCachedThreadPool();

        System.out.println("Server start....");

        while (true) {
            //3.随时准备接收客户端过来的请求
            final Socket socket = serverSocket.accept();
            System.out.println("Connect to a client...");

            threadPool.submit(() -> {
                //数据处理(接收客户端的数据并进行响应)
                handle(socket);
            });
        }
    }

    public static void handle(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            //获取输入流
            InputStream is = socket.getInputStream();
            while (true) {
                //从输入流中读取数据到bytes数组
                int read = is.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, bytes.length));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
