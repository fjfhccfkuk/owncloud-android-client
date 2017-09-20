/**
 * Created by hsx on 17-6-6.
 */
package com.han.commu;

import android.content.Context;

import com.han.utils.HanLog;

import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class SockServer extends AbsSocket {

    private Selector mSockSelector = null;
    private Thread mSerThr = null;
    private ServerSocket mSock = null;
    private Map<SocketChannel, ClientSession> mClientList = new HashMap<SocketChannel, ClientSession>();

    private static String grantAppList[] = new String[]{
            "com.busap.remctrl"
    };


    public SockServer() {
//        HanLog.write("CenterServer", " ");
        mSerThr = new Thread(new ServerRunnable());
    }

    public void startServer() {
        if (null == mSerThr)
            return;
        mSerThr.start();
    }

    protected static final boolean passPrivilegeCensor(Context c) {
        boolean bRet = false;
        String packName = c.getPackageName();

        do {
            if (packName == null || "".equals(packName))
                break;

                for (String n : grantAppList) {
                    if (n.equals(packName)) {
                        bRet = true;
                        break;
                    }
                }
        }while(false);

        return bRet;
    }

    public class ClientSession {
        private ClientSession(){}
    }

    private boolean initServer() {
        ServerSocketChannel rootChnel = null;

        try {
            mSockSelector = Selector.open();
            if (!mSockSelector.isOpen()) {
                HanLog.write("CenterServer", " selector is closed.");
                return false;
            }

            rootChnel = ServerSocketChannel.open();
            rootChnel.configureBlocking(false);
//            rootChnel.socket().bind(new InetSocketAddress(LOCAL_SER_IP, LOCAL_SER_PORT));
            rootChnel.register(mSockSelector, SelectionKey.OP_ACCEPT);
        } catch (Exception e){
            HanLog.write("CenterServer", " 创建服务端socket失败,将被关闭。Excp:" + e.toString());
            try {
                rootChnel.socket().close();
                rootChnel.close();
            } catch (Exception e1){}
            return false;
        }

        return true;
    }



    abstract void handleClientSession(ClientSession cs);

    private void handleSelecttionKey(SelectionKey k) {
        if (k.isAcceptable()) {
            try {
                ServerSocketChannel sockChnel = (ServerSocketChannel) k.channel();
                SocketChannel newChnel = sockChnel.accept();
                newChnel.configureBlocking(false);
                newChnel.register(mSockSelector, SelectionKey.OP_READ);

                //将新的socket channel 与ClientSession绑定 通过map

            }catch(Exception e){
                HanLog.write("CenterServer", " handleSelecttionKey() excp:" + e.toString());
            } finally {
                return;
            }
        }

        //获取发生事件的 socket channel
        SocketChannel sChnel = (SocketChannel) k.channel();

        //获取socket channel 对应的 ClientSession
        ClientSession cs = null;//

        //处理ClientSession
        handleClientSession(cs);
    }

    private void handleSocketChannel() {
        if (null == mSockSelector || !mSockSelector.isOpen()) {
            HanLog.write("CenterServer"," handleSocketChannel() selector invalid.");
            return;
        }

        while(true) {
            try {
                mSockSelector.select();
                Iterator<SelectionKey> keyIte = mSockSelector.selectedKeys().iterator();

                while(keyIte.hasNext()) {
                    SelectionKey key = keyIte.next();
                    if (!key.isValid()) {
                        keyIte.remove();
                        continue;
                    }

                    handleSelecttionKey(key);
                }
            }catch (Exception e) {}
        }
    }

    abstract void handleServerMessage(String s);

    //////////////////////////////////////////////////
    private class ServerRunnable implements Runnable {

        @Override
        public void run() {
            if (!initServer()) {
                HanLog.write("CenterServer", " Failed to init CenterServer");
                return;
            }

            handleSocketChannel();

            //正常情况，不会到达这里
            throw  new RuntimeException("!!!!!!!  CenterServer exit, Fatal error !!!!!!");
        }
    }
}
