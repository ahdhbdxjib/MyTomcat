package com.study.testBS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class testServer {
    public static void main(String[] args) throws IOException {
        //创建一个socket的服务的对象
        ServerSocket serverSocket = null;
        Socket socket = null;
        //输入输出流
        OutputStream ops = null;
        try {
            serverSocket = new ServerSocket(8080);
            while(true){
                //启用服务一直监听
                socket = serverSocket.accept();
                System.out.println("客户端请求");
                //获取服务器的输入流和输出流
                ops = socket.getOutputStream();
                //响应头部分
                ops.write("HTTP/1.1 200 OK\n".getBytes());
                ops.write("Content-Type: text/html;charset=utf-8\n".getBytes());
                ops.write("Server:Apche\n".getBytes());
                ops.write("\n\n".getBytes());
                //响应的消息体部分
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("<html>");
                stringBuffer.append("<head>");
                stringBuffer.append("</head>");
                stringBuffer.append("<body>");
                stringBuffer.append("<h1>I am Header</h1>");
                stringBuffer.append("<a href='http://www.baidu.com'>Baidu</a>");
                stringBuffer.append("</body>");
                stringBuffer.append("</html>");
                ops.write(stringBuffer.toString().getBytes());
                ops.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭所有的资源
            if (null != socket) {
                socket.close();
                socket = null;
            }
            if(null != ops){
                ops.close();
                ops = null;
            }
        }
    }
}
