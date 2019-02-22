package MyToncatv1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    //创建的变量用于存放WebContent的绝对路径
    static String WEB_ROOT = System.getProperty("user.dir") + "\\" + "WebContent";
    //用于存放请求静态页面的名称
    static String uri = "";

    public static void main(String[] args) throws IOException {
//        System.out.println(WEB_ROOT);
        //创建一个服务器的socket对象
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        OutputStream ops = null;

        try {
            serverSocket = new ServerSocket(8080);
            //用于多个客户的请求
            while (true) {
                //用于接受客户端的请求,并获取客户的socket
                socket = serverSocket.accept();
                //获取输出输入流
                is = socket.getInputStream();
                ops = socket.getOutputStream();
                //获取http请求的请求头的部分，截取资资源的名称，并将其赋值给uri
                parse(is);
                //发送静态资源
                sendStaticResourse(ops);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭相关的资源

            if (null != is) {
                is.close();
                is = null;
            }
            if (null != ops) {
                ops.close();
                ops = null;
            }
            if (null != socket) {
                socket.close();
                socket = null;
            }
        }
    }

    //发送静态资源
    private static void sendStaticResourse(OutputStream ops) throws IOException {
        byte[] bytes = new byte[2048];
        FileInputStream fis = null;
        try {
            File file = new File(WEB_ROOT, uri);
            //如果文件存在就将相关网页返回给客户端
            if (file.exists()) {
                //打印响应头
                ops.write("HTTP/1.1 200 OK\n".getBytes());
                ops.write("Content-Type: text/html;charset=utf-8\n".getBytes());
                ops.write("Server:Apche\n".getBytes());
                ops.write("\n\n".getBytes());
                fis = new FileInputStream(file);
                int ch = fis.read(bytes);
                while (ch != -1) {
                    //发送 页面，从0 - 文件的长度
                    ops.write(bytes, 0, ch);
                    ch = fis.read(bytes);
                }

            } else {
                //提示用户没有找到相关页面
                ops.write("HTTP/1.1 104 Notfound\n".getBytes());
                ops.write("Content-Type: text/html;charset=utf-8\n".getBytes());
                ops.write("Server:Apche\n".getBytes());
                ops.write("\n".getBytes());
                String ErroMesage = "文件没有找到";
                ops.write(ErroMesage.getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (null != fis) {
                fis.close();
                fis = null;
            }
            if (null != ops) {
                ops.close();
                ops = null;
            }
        }

    }

    //获取http请求的请求头的部分，截取资资源的名称，并将其赋值给uri
    private static void parse(InputStream is) throws IOException {
        //顶一个变量，用于获取响应头部分的数据
        StringBuffer content = new StringBuffer(2048);
        //定义一个数组用于暂时存取数据
        byte[] buffer = new byte[2048];
        //int形式的变量存取数据的长度
        int i = -1;
        //i 表示所有字符总长度，buffer读取字符存放的位置
        i = is.read(buffer);
        for (int j = 0; j < i; j++) {
            content.append((char) buffer[j]);
        }
        System.out.println(content);
        parseUri(content);
    }

    //在响应头获取到访问文件的路径
    private static void parseUri(StringBuffer content) {
        int index1, index2;
        index1 = content.indexOf(" ");
        if (index1 > -1) {
            index2 = content.indexOf(" ", index1 + 1);
            if (index2 > index1) {
                uri = content.substring(index1 + 2, index2);
                System.out.println(uri);
            }
        } else {
            System.out.println("用户没有请求数据");
        }
    }
}
