package com.yidao.module_lib.base.http;

/**
 * Created by dream on 2017/7/20.
 * 请求地址的配置
 */

public interface ILocalHost {


    String getVersion = "/p-80101"; //版本


    String createDevice = "/update_device";  //创建设备

    String createDeviceBox = "/update_device_box";  //根据设备id创建箱门

    String getDeviceInfo = "/get_device_info";  //获取设备信息

    String getDeviceBoxInfo = "/get_device_box_info";  //获取设备箱门信息

    String updateDeviceBoxStatus = "/update_device_box_openstatus";  //更新设备箱门状态



    String myHostUrl = "http://txe.ralfqbk.xyz/index.php?s=/Apidevice";
}
