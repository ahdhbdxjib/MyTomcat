package com.study.testBS;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPSize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) throws Exception {
        Socket socket = null;
        InputStream is = null;
        OutputStream ops = null;
        try {
            //1.使用socket对象，连接一个百度的端口
            socket = new Socket("www.baidu.com",80);
            //2.获取输出流和输出流
            is = socket.getInputStream();
            ops = socket.getOutputStream();
            ops.write("GET / HTTP/1.1\n".getBytes());
            ops.write("Host: www.baidu.com\n".getBytes());
            ops.write("\n".getBytes());
            int i = is.read();
            while ( -1 != i){
                System.out.print((char)i);
                i = is.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            if(null != socket){
                socket.close();
                socket = null;
            }
            if(null != is){
                is.close();
                is = null;
            }if(null != ops){
                ops.close();
                ops = null;
            }
        }
    }
}
