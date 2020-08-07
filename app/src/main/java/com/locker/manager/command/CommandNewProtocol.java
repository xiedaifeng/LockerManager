package com.locker.manager.command;

import android.text.TextUtils;
import android.util.Log;

import com.qiao.serialport.utils.ByteUtil;

import java.util.HashMap;
import java.util.Map;

public class CommandNewProtocol {

    private static final byte HEAD_PROTOCOL=0x55;

    public static final byte COMMAND_OPEN=(byte)0xA1;
    public static final byte COMMAND_SELECT_BOX_STATE=(byte)0xA2;

    public static final byte COMMAND_OPEN_RESPONSE= (byte) 0xA1;
    public static final byte COMMAND_SELECT_DEPOSIT_STATE= (byte) 0xA2;

    public static final byte COMMAND_SET_BOX_START=(byte)0xA6;
    public static final byte COMMAND_SET_BOX_END=(byte)0xA4;

    public static final byte COMMAND_FIXED = 0x00;
//    命令字	功能说明
//0xA1	开指定箱门
//0xA1	回应开门结果
//0xA2	查询箱门状态
//0xA2	回应查询的箱门状态结果，
//0x25	查询箱门存物状态,data为指定的箱门状态，两个字节
//0xA5	回应查询的存物状态结果，data为起始箱号+所有的箱门存物状态
//0xA6	设置锁板的起始箱号
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
    public CommandNewProtocol(Builder builder){
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
        public CommandNewProtocol builder(){
            int boxCh=0;
            if (commandChannel instanceof Integer){
                boxCh=Integer.parseInt((int)commandChannel+"",16);
            }else if (commandChannel instanceof String){
                boxCh=Integer.parseInt((String) commandChannel, 16);
            }
            byte[] bytesBox= new byte[5];
            byte byte3 = 0;
            if(command == COMMAND_OPEN){
                byte3 = 0x5F;
            } else if(command == COMMAND_SELECT_BOX_STATE){
                byte3 = 0x00;
            } else if(command == COMMAND_SET_BOX_START){
                byte3 = (byte) boxCh;
                boxCh = 0x00;
            } else if(command == COMMAND_SET_BOX_END){
                byte3 = (byte) boxCh;
                boxCh = 0x00;
            }
            bytesBox[0] = HEAD_PROTOCOL;
            bytesBox[1] = (byte)boxCh;
            bytesBox[2] = command;
            bytesBox[3] = byte3;
            bytesBox[4] = COMMAND_FIXED;
            byte[] ret = new byte[bytesBox.length+1];
            System.arraycopy(bytesBox,0,ret,0,bytesBox.length);
            ret[ret.length-1] = ByteUtil.getXor(bytesBox, bytesBox.length);

            this.bytes= ret;
            Log.e("newBulider",ByteUtil.ByteArrToHex(this.bytes));
            return new CommandNewProtocol(this);
        }

        public CommandNewProtocol parseMessage(){
            if (bytes==null){
                this.error=0x0A;
                return new CommandNewProtocol(this);
            }
            if ( bytes.length < 4) {
                this.error=0x0B;
                return new CommandNewProtocol(this);
            }
            if (ByteUtil.getXor(bytes, bytes.length-1)!=bytes[bytes.length-1]){
                this.error=0x0C;
                return new CommandNewProtocol(this);
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
            return new CommandNewProtocol(this);
        }
    }

}
