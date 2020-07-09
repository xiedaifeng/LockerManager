package com.qiao.serialport.bean;

public enum  Command {


    WRITE_ONE_LOCKER_CHANNEL(0x82),
    READ_ONE_LOCKER_CHANNEL(0x83),
    READ_ALL_LOCKER_CHANNEL(0x84),
    WRITE_ALL_LOCKER_CHANNEL(0x86),
    WRITE_MORE_LOCKER_CHANNEL(0x87),
    LOCKER_CHANNEL_OUTPUT(0x88),
    LOCKER_CHANNEL_CLOSE(0x89);
    private int command=0;
    Command(int command) {
        this.command=command;
    }

    public int getCommand() {
        return command;
    }
}
