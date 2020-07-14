package com.locker.manager.command;

import android.text.TextUtils;
import android.util.Log;

import com.qiao.serialport.utils.ByteUtil;
import com.qiao.serialport.utils.Consts;

import java.util.Arrays;

/**
 * 此类用于锁的智能化解析
 */
public class Command {
    public static interface Consts{
        public static String COMMAND_1="01";
        public static String COMMAND_2="02";
        public static String COMMAND_3="03";
        public static String COMMAND_4="04";
        public static String COMMAND_5="05";
        public static String COMMAND_6="06";
        public static String COMMAND_7="07";
        public static String COMMAND_8="08";
        public static String COMMAND_9="09";
        public static String COMMAND_10="0A";
        public static String COMMAND_11="0B";
        public static String COMMAND_12="0C";
        /**
         *查询状态指令
         */
        public static String COMMAND_READ="A2";
        /**
         * 开锁指令
         */
        public static String COMMAND_WRITE="A1";
        /**
         * 设置开始指令
         */
        public static String COMMAND_SETTING_START="A6";
        /**
         * 设置结束指令
         */
        public static String COMMAND_SETTING_END="A4";
    }


    /**
     * 解析数据错误码
     * 0x01 1代表成功
     * 0x0A 10 代表byte数组为空
     * 0x0B 11 代表校验和错误
     *
     */
    private int error=0x00;


    /**
     * 锁的通讯协议头
     */
    private static String command_header="55";

    private byte[]bytes=null;


    private String commandFixed_1="00";
    private String commandFixed_2="00";
    /**
     * 锁的通道号
     */
    private String commmandChannel="01";
    /**
     * 锁的开关状态
     */
    private String commandState="关";
    /**
     * 命令
     */
    private String command="01";




    public int getError() {
        return error;
    }



    public String getCommand() {
        return command;
    }


    public String getCommandFixed_1() {
        return commandFixed_1;
    }

    public String getCommandFixed_2() {
        return commandFixed_2;
    }

    public String getCommandState() {
        return commandState;
    }

    public String getCommmandChannel() {
        return commmandChannel;
    }

    @Override
    public String toString() {
        return "Command{" +
                "error=" + error +
                ", bytes=" + Arrays.toString(bytes) +
                ", commandFixed_1='" + commandFixed_1 + '\'' +
                ", commandFixed_2='" + commandFixed_2 + '\'' +
                ", commmandChannel='" + commmandChannel + '\'' +
                ", commandState='" + commandState + '\'' +
                ", command='" + command + '\'' +
                '}';
    }

    public Command (Builder builder){
        commmandChannel=builder.commmand_channel;
        commandFixed_1=builder.command_fixed_1;
        commandFixed_2=builder.command_fixed_2;
        command=builder.command;
        bytes=builder.bytes;
        commandState=builder.command_state;
        error=builder.error;
    }


    public static class Builder{

        private byte[] bytes=null;

        public Builder setBytes(byte[] bytes) {
            this.bytes = bytes;



            return this;
        }

        private int error=0x00;


        private String command_fixed_1="00";
        private String command_fixed_2="00";
        private String commmand_channel="01";
        private String command_state="关";
        /**
         * 命令
         */
        private String command="01";

        public Builder setError(int error) {
            this.error = error;
            return this;
        }

        /**
         * 开箱号
         * @param commmand_channel
         * @return
         */
        public Builder setCommmandChannel(String commmand_channel) {
            this.commmand_channel = commmand_channel;
            return this;
        }

        public Builder setCommandFixed_1(String command_fixed_1) {
            this.command_fixed_1 = command_fixed_1;
            return this;
        }

        public Builder setCommandFixed_2(String command_fixed_2) {
            this.command_fixed_2 = command_fixed_2;
            return this;
        }

        public Builder setCommandState(String command_state) {
            this.command_state = command_state;
            return this;
        }



        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }



        public Command build() {
            try {
                String [] strings=new String[]{command_header,commmand_channel,command,command_fixed_1,command_fixed_2};
                byte[] bytes=new byte[strings.length+1];
                byte sum=0;
                for(int i=0;i<strings.length;i++){
                    bytes[i]=(byte)Integer.parseInt(strings[i],16);
                    sum^=((byte)Integer.parseInt(strings[i],16));

                }
                bytes[strings.length]=sum;
                this.bytes=bytes;
            }catch (Exception e){
                e.printStackTrace();
            }
            return new Command(this);
        }



        public Command parse(){

            try {
                if (bytes==null){
                    this.error=0x0A;
                }
                byte sum=0;
                if (bytes.length>0){
                    for (int i=0;i<bytes.length-1;i++){
                        sum^=bytes[i];
                    }
                    if (sum==bytes[bytes.length-1]){
                        this.error=0x01;
                        this.command=String.format("%02x", new Object[]{bytes[2]}).toUpperCase();
                        this.commmand_channel=String.format("%02x", new Object[]{bytes[1]}).toUpperCase();
                        this.command_fixed_1=String.format("%02x", new Object[]{bytes[3]}).toUpperCase();
                        this.command_fixed_2=String.format("%02x", new Object[]{bytes[4]}).toUpperCase();
                        if (TextUtils.equals(command_fixed_2,"70")){
                            this.command_state="开";
                        }else if (TextUtils.equals(command_fixed_2,"7F")){
                            this.command_state="关";
                        }
                    }else {
                        this.error=0x0B;
                    }
                }else {
                    this.error=0x0A;
                }
            }catch (Exception e){
                e.printStackTrace();
                this.error=0x0A;
            }
            return new Command(this);

        }



    }

    public byte[] getBytes() {
        return bytes;
    }
}
