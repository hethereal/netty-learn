package com.qiantangnotes.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        //服务器端的启动器：负责组装netty组件，启动服务器
        new ServerBootstrap()
            //使用selector监听IO事件，每一个线程处理一件事
            .group(new NioEventLoopGroup())
            //选择服务器的ServerSocketChannel实现
            .channel(NioServerSocketChannel.class)
            // boss负责处理连接，child负责读写，决定了child能执行哪些操作
                //代表和客户端进行数据读写的通道 Initializer 初始化器，负责添加别的Handler
            .childHandler(new ChannelInitializer<NioSocketChannel>() {

                @Override
                protected void initChannel(NioSocketChannel channel) throws Exception {
                    // 添加具体的handler
                    // 将传输过来的ByteBuf转换为字符串
                    channel.pipeline().addLast(new StringDecoder());
                    //自定义的handler
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        //读事件
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //打印上一步转换好的字符串
                            System.out.println(msg);
                        }
                    });
                }
            })
            .bind(8080);
    }
}
