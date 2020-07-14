package com.locker.manager.command;

import android.os.Message;
import android.text.TextUtils;

import com.qiao.serialport.utils.ByteUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandProtocol {

    private static final byte HEAD_PROTOCOL=0x5A;

//    命令字	功能说明
//0x21	开指定箱门
//0xA1	回应开门结果
//0x22	查询箱门状态
//0xA2	回应查询的箱门状态结果，
//            0x25	查询箱门存物状态,data为指定的箱门状态，两个字节
//0xA5	回应查询的存物状态结果，data为起始箱号+所有的箱门存物状态
//0x23	设置锁板的起始箱号
//0xA3	回应设置锁板起始箱号
    private int error=0x0A;

    private String command="";

    private String commandChannel="";

    private String state="";

    private byte[] bytes=null;

    public int getError() {
        return error;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Map<String, String> getData() {
        return maps;
    }

    public String getCommand() {
        return command;
    }

    public String getCommandChannel() {
        return commandChannel;
    }

    public String getState() {
        return state;
    }

    /**
     * 设备状态参数
     */
    private Map<String,String> maps=null;
    public CommandProtocol(Builder builder){
        this.command=builder.command;
        this.bytes=builder.bytes;
        this.commandChannel=builder.commandChannel;
        this.maps=builder.data;
        this.error=builder.error;
    }

    public static class Builder{

        private int error=0x0A;

        private String command="";

        private String commandChannel="";

        private byte[] bytes=null;
        private String state="";

        private Map<String,String> data=new HashMap<>();

        public Builder setBytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        public Builder setError(int error) {
            this.error = error;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setData(Map<String, String> maps) {
            this.data = maps;
            return this;
        }

        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }
        public Builder setCommandChannel(String commandChannel) {
            this.commandChannel = commandChannel;
            return this;
        }
        public CommandProtocol builder(){
           byte[] bytes= ByteUtil.decStr2bytes(commandChannel);
            ByteBuffer byteBuffer=ByteBuffer.allocate(1+bytes.length);
            if (bytes==null){
                byteBuffer.put(HEAD_PROTOCOL);
                byteBuffer.put((byte)Integer.parseInt(command, 16));
                byteBuffer.put(bytes);
                byte[] byteArray= byteBuffer.array();
                byteBuffer.put( ByteUtil.getXor(byteArray,byteArray.length));
                byteBuffer.flip();
               this.bytes= byteBuffer.array();
            }
            return new CommandProtocol(this);
        }

        public CommandProtocol parseMessage(){
            if (bytes==null){
                this.error=0x0A;
                return new CommandProtocol(this);
            }
            if (ByteUtil.getXor(bytes, bytes.length)!=bytes[bytes.length-1]){
                this.error=0x0B;
                return new CommandProtocol(this);
            }
            this.command=String.format("%02x", new Object[]{bytes[1]}).toUpperCase();
            if (!TextUtils.isEmpty(command)){
                if (TextUtils.equals("A1", command)){
                    this.state=String.format("%02x", new Object[]{bytes[2]}).toUpperCase();
                }else if (TextUtils.equals("A2", command)){
                    int start = (bytes[2] & 255) * 256 + (bytes[3] & 255);
                    int end = (bytes[4] & 255) * 256 + (bytes[5] & 255);
                    int pos = 0;
                    byte [] stat=new byte[bytes.length-3];
                    System.arraycopy(bytes, 6, stat, 0, bytes.length - 7);
                    for(int i = start; i <= end; ++i) {
                        data.put(i+"", (stat[pos / 8] & 1 << pos % 8) > 0 ? 1+"" : 0+"");
                        ++pos;
                    }
                }else if (TextUtils.equals("A3", command)){
                    this.state=String.format("%02x", new Object[]{bytes[2]}).toUpperCase();
                }else if (TextUtils.equals("A5", command)){
                    int start = (bytes[2] & 255) * 256 + (bytes[3] & 255);
                    int end = (bytes[4] & 255) * 256 + (bytes[5] & 255);
                    int pos = 0;
                    byte [] stat=new byte[bytes.length-3];
                    System.arraycopy(bytes, 6, stat, 0, bytes.length - 7);
                    for(int i = start; i <= end; ++i) {
                        data.put(i+"", (stat[pos / 8] & 1 << pos % 8) > 0 ? 1+"" : 0+"");
                        ++pos;
                    }
                }
            }
            return new CommandProtocol(this);
        }
    }

    public static HashMap<Integer, Integer> ParseGetBoxStatus(byte[] bytes, int len) {
        HashMap<Integer, Integer> status = new HashMap();
        if (ParseProtocol(bytes, len, (byte)-94)) {
            int start = (bytes[2] & 255) * 256 + (bytes[3] & 255);
            int end = (bytes[4] & 255) * 256 + (bytes[5] & 255);
            int pos = 0;
            byte[] stat = new byte[len - 7];
            System.arraycopy(bytes, 6, stat, 0, len - 7);
            for(int i = start; i <= end; ++i) {
                status.put(i, (stat[pos / 8] & 1 << pos % 8) > 0 ? 1 : 0);
                ++pos;
            }
        }

        return status;
    }

    private static boolean ParseProtocol(byte[] dat, int len, byte cmd) {
        if (dat != null && len >= 4 && dat.length >= len) {
            if (ByteUtil.getXor(dat, len) != 0) {
                return false;
            } else {
                return dat[1] == cmd;
            }
        } else {
            return false;
        }
    }
}
