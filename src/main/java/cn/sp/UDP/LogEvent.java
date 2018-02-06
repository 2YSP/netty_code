package cn.sp.UDP;

import java.net.InetSocketAddress;

/**
 * @Author: 2YSP
 * @Description: 消息POJO
 * @Date: Created in 2018/2/6
 */
public class LogEvent {

    public static  final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String logfile,String msg){
        this(null,-1,logfile,msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.received = received;
        this.logfile = logfile;
        this.msg = msg;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceivedTimeStamp(){
        return received;
    }
}
