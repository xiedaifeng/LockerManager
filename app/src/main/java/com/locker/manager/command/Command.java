package com.locker.manager.command;

import android.util.Log;

import com.qiao.serialport.utils.ByteUtil;
import com.qiao.serialport.utils.Consts;

import java.util.Arrays;

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
         *
         */
        public static String COMMAND_READ="A2";
        public static String COMMAND_WRITE="A1";
        public static String COMMAND_SETTING_START="A6";
        public static String COMMAND_SETTING_END="A4";

    }



    private int error=0x00;



    private static String command_header="55";

    private byte[]bytes=null;

    private String fixed_1="00";
    private String fixed_2="00";
    private String box="01";
    /**
     * 命令
     */
    private String command="01";


    public int getError() {
        return error;
    }

    public String getBox() {
        return box;
    }

    public String getCommand() {
        return command;
    }

    public String getFixed_1() {
        return fixed_1;
    }

    public String getFixed_2() {
        return fixed_2;
    }

    @Override
    public String toString() {
        return "Command{" +
                "error=" + error +
                ", bytes=" + Arrays.toString(bytes) +
                ", fixed_1='" + fixed_1 + '\'' +
                ", fixed_2='" + fixed_2 + '\'' +
                ", box='" + box + '\'' +
                ", command='" + command + '\'' +
                '}';
    }

    public Command (Builder builder){
        box=builder.box;
        fixed_1=builder.fixed_1;
        fixed_2=builder.fixed_2;
        command=builder.command;
        bytes=builder.bytes;
    }


    public static class Builder{

        private byte[] bytes=null;

        public Builder setBytes(byte[] bytes) {
            this.bytes = bytes;
            if (bytes==null){
                this.error=0x10;
            }
            byte sum=0;


            if (bytes.length>0){
                for (int i=0;i<bytes.length-1;i++){
                    sum^=bytes[i];
                }

                if (sum==bytes[bytes.length-1]){
                    this.error=0x01;
                    this.command=String.format("%02x", new Object[]{bytes[2]}).toUpperCase();
                    this.box=String.format("%02x", new Object[]{bytes[1]}).toUpperCase();
                    this.fixed_1=String.format("%02x", new Object[]{bytes[3]}).toUpperCase();
                    this.fixed_2=String.format("%02x", new Object[]{bytes[4]}).toUpperCase();

                }
            }


            return this;
        }

        private int error=0x00;

        private String fixed_1="00";
        private String fixed_2="00";
        private String box="01";
        /**
         * 命令
         */
        private String command="01";

        public Builder setError(int error) {
            this.error = error;
            return this;
        }

        public Builder setBox(String box) {
            this.box = box;
            return this;
        }

        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }

        public Builder setFixed_1(String fixed_1) {
            this.fixed_1 = fixed_1;
            return this;
        }

        public Builder setFixed_2(String fixed_2) {
            this.fixed_2 = fixed_2;
            return this;
        }

        public Command build() {
            return new Command(this);
        }

        public Builder getBulider(){
            String [] strings=new String[]{command_header,box,command,fixed_1,fixed_2};
            byte[] bytes=new byte[strings.length+1];
            byte sum=0;
            for(int i=0;i<strings.length;i++){
                bytes[i]=(byte)Integer.parseInt(strings[i],16);
                sum^=((byte)Integer.parseInt(strings[i],16));

            }
            bytes[strings.length]=sum;
            this.bytes=bytes;
            return this;
        }



    }

    public byte[] getBytes() {
        return bytes;
    }
}
