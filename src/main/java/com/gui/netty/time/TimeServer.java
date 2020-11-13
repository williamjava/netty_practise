package com.gui.netty.time;

import com.gui.netty.discard.DiscardServletHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 丢弃服务器
 *
 * @author whj
 */
public class TimeServer {
    private int port;

    public TimeServer(int port){
        this.port = port;
    }

    public void run() throws Exception {
        System.out.println("TimeServer is running.....");
        //多线程事件循环器 - 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //多线程事件循环器 - 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动NIO服务的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new TimeServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            //等待服务器，socket关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8083;
        }

        new TimeServer(port).run();
    }
}
