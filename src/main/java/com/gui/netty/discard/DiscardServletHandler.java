package com.gui.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServletHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每当从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * 这里收到的消息类型为ByteBuf
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("accept new msg......");
        ByteBuf inBuf = (ByteBuf) msg;
        try {
            StringBuilder sb = new StringBuilder();
            while (inBuf.isReadable()) {
                System.out.println((char)inBuf.readByte());
                System.out.flush();
            }
        } finally {
            //默默地丢弃收到的数据
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当出现 Throwable 对象才会被调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
