package com.han.utils.HanCrash;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EMail{
    private Context context;
    private MailSenderInfo info;
    private Properties properties;
    private Session session;
    private Message message;
    private MimeMultipart multipart;
    private final String attachmentName = "/storage/sdcard0/log_new/Crash_remctrlser.log";//附件路径


    //发件人
    private  final String userEmail = "busapcrash@163.com";
    //发件人密码
    private  final String userPas = "busonline";
    //收件人
    private  final String sendTo = "shuangxi.han@busonline.com";
    //抄送人
    private  final String  CC = "shihuan.han@busonline.com";
    //协议
    private  final String Type = "smtp.163.com";


    public EMail(Context context){
        this.context = context;
        this.multipart = new MimeMultipart("mixed");
    }

    public boolean sendEMail() {

        if (!isFileExist(attachmentName)) {
            return true;
        }

/*        if (!isSendEMail()){
            Log.e("sendEMail","return");
            return false;
        }*/

        Log.e("sendEMail","开始发：：：：：");
        info = new MailSenderInfo();
        if("smtp.163.com".equals(Type)){
            info.setMailServerHost("smtp.163.com");
        }else if("smtp.qq.com".equals(Type)){
            info.setMailServerHost("smtp.qq.com");
        }

        //info.setMailServerHost(Type);
        info.setMailServerPort("25");
        info.setValidate(true);
        info.setUserName(userEmail);
        info.setPassword(userPas);//您的邮箱密码
        info.setFromAddress(userEmail);
        info.setToAddress(sendTo);
        info.setSubject("Rem错误日志：");
        info.setContent("喜子查收日志：");
        try{
            if(sendTextMail(info)){
                Log.e("--------","发送成功");
                //Toast.makeText(context, "发送成功", 1).show();
                deleteFile(attachmentName);
            }else{
                Log.e("--------","发送失败");
                //Toast.makeText(context, "发送失败", 1).show();
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
		/*if(!TextUtils.isEmpty(attachmentName)&& file != null){
			deleteFile(file);
		}*/

		return true;
    }







    /**
     * 邮件设置(发件人、标题、内容)
     * @throws MessagingException
     * @throws AddressException
     */
    private void setMessage(){
        try {
            this.message.setFrom(new InternetAddress(info.getFromAddress()));
            //设置抄送人的电子邮件
            InternetAddress[] addresses = new InternetAddress[1];
            addresses[0] = new InternetAddress(CC);
            this.message.setRecipients(Message.RecipientType.CC, addresses);
            this.message.setSubject(info.getSubject());
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(info.getContent(), "text/html;charset=gbk");
            this.multipart.addBodyPart(bodyPart);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 添加附件
     * @throws MessagingException
     */
    private void addAttachment(){
        try {
            if (!TextUtils.isEmpty(attachmentName)) {
                FileDataSource fileDataSource = new FileDataSource(new File(attachmentName));
                DataHandler dataHandler = new DataHandler(fileDataSource);
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setDataHandler(dataHandler);
                mimeBodyPart.setFileName(fileDataSource.getName());
                this.multipart.addBodyPart(mimeBodyPart);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean sendTextMail(MailSenderInfo mailInfo)
    {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/mixed;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        properties = mailInfo.getProperties();
        if (mailInfo.isValidate())
        {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        session = Session.getDefaultInstance(properties,authenticator);
        try
        {
            // 根据session创建一个邮件消息
            message = new MimeMessage(session);
            setMessage();
            addAttachment();
            // 设置邮件消息发送的时间
            message.setSentDate(new Date());
            message.setContent(multipart);//发送的内容及附件等
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            message.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            message.setRecipient(Message.RecipientType.TO,to);
            message.saveChanges();
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (MessagingException ex) {
            Log.e("----",""+ex.toString());
        }
        return false;
    }
    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) { // 判断文件是否存在
            file.delete();
        }
    }

    public boolean isFileExist(String f) {
        File file = new File(f);
        return file.exists();
    }

    /**
     * 在wifi情况下，carch文件存在的情况下
     * 发送
     * @return
     */
    private boolean isSendEMail(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//        if(wifiNetworkInfo.isConnected()||isFileExist(attachmentName))
        {
            return true ;
        }

//        return false ;

    }

}