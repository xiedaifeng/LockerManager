package com.locker.manager.command;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.qiao.serialport.utils.ByteUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableOnSubscribe;

public class CommandProtocol {

    private static final byte HEAD_PROTOCOL=0x5A;
    public static final byte COMMAND_OPEN=0x21;
    public static final byte COMMAND_SELECT_BOX_STATE=0x22;
    public static final byte COMMAND_SELECT_DEPOSIT_STATE= (byte) 0xA2;
    public static final byte COMMAND_SET_BOX_START=  0x23;
    public static final byte COMMAND_SET_BOX_END=(byte)0xA3;
//    命令字	功能说明
//0x21	开指定箱门
//0xA1	回应开门结果
//0x22	查询箱门状态
//0xA2	回应查询的箱门状态结果，
//            0x25	查询箱门存物状态,data为指定的箱门状态，两个字节
//0xA5	回应查询的存物状态结果，data为起始箱号+所有的箱门存物状态
//0x23	设置锁板的起始箱号
//0xA3	回应设置锁板起始箱号
    private int error=0x01;

    private byte command=0x00;

    private String commandStr="";

    private Object commandChannel="";

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

    public byte getCommand() {
        return command;
    }

    public Object getCommandChannel() {
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
        this.state=builder.state;
        this.error=builder.error;
        this.commandStr=builder.commandStr;
    }

    public static class Builder{

        public String commandStr="";
        private int error=0x0A;

        private byte command=0x00;

        private Object commandChannel="";

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

        public Builder setCommand(byte command) {
            this.command = command;
            return this;
        }

        /**
         * 箱号
         * @param commandChannel
         * @return
         */
        public Builder setCommandChannel(Object commandChannel) {
            this.commandChannel = commandChannel;
            return this;
        }
        public CommandProtocol builder(){
            int boxCh=0;
            if (commandChannel instanceof Integer){
                boxCh=Integer.parseInt((int)commandChannel+"",16);;
            }else if (commandChannel instanceof String){
                boxCh=Integer.parseInt((String) commandChannel, 16);
            }
           byte[] bytesBox= new byte[]{(byte)(boxCh / 256), (byte)(boxCh % 256)};
            if (bytesBox!=null){
                byte[] ret = new byte[bytesBox.length + 3];
                ret[0] = HEAD_PROTOCOL;
                ret[1] = command;
                System.arraycopy(bytesBox, 0, ret, 2, bytesBox.length);
                ret[bytesBox.length + 2] = ByteUtil.getXor(ret, bytesBox.length + 2);
               this.bytes= ret;
            }
            Log.e("bulider",ByteUtil.ByteArrToHex(this.bytes));
            return new CommandProtocol(this);
        }

        public CommandProtocol parseMessage(){
            if (bytes==null){
                this.error=0x0A;
                return new CommandProtocol(this);
            }
            if ( bytes.length < 4) {
                this.error=0x0B;
                return new CommandProtocol(this);
            }
            if (ByteUtil.getXor(bytes, bytes.length-1)!=bytes[bytes.length-1]){
                this.error=0x0C;
                return new CommandProtocol(this);
            }
            this.command=bytes[1];
            this.commandStr=String.format("%02x", new Object[]{bytes[1]}).toUpperCase();
            if (!TextUtils.isEmpty(commandStr)){
                if (TextUtils.equals("A1", commandStr)){
                    this.state=String.format("%02x", new Object[]{bytes[2]}).toUpperCase();
                }else if (TextUtils.equals("A2", commandStr)){
                    int start = (bytes[2] & 255) * 256 + (bytes[3] & 255);
                    int end = (bytes[4] & 255) * 256 + (bytes[5] & 255);
                    int pos = 0;
                    byte [] stat=new byte[bytes.length-3];
                    System.arraycopy(bytes, 6, stat, 0, bytes.length - 7);
                    for(int i = start; i <= end; ++i) {
                        data.put(i+"", (stat[pos / 8] & 1 << pos % 8) > 0 ? 1+"" : 0+"");
                        ++pos;
                    }
                }else if (TextUtils.equals("A3", commandStr)){
                    this.state=String.format("%02x", new Object[]{bytes[2]}).toUpperCase();
                }else if (TextUtils.equals("A5", commandStr)){
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

}
