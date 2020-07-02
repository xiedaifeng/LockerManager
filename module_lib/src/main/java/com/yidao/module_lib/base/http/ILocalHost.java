package com.yidao.module_lib.base.http;

/**
 * Created by dream on 2017/7/20.
 * 请求地址的配置
 */

public interface ILocalHost {

    String sendVCode = "/p-101"; //发送验证码

    String login = "/p-102";  //登录

    String checkVCode = "/p-105"; //校验验证码是否正确

    String getMyInfo = "/s-10302"; //我的验证信息

    String messageInput = "/s-10002"; //演员信息录入

    String crewmessageInput = "/s-10002"; //剧组信息录入

    String addcrew = "/s-20002"; //添加剧组

    String getMessageList = "/s-40001";  //获取消息列表

    String getUserInfo = "/s-10001";  //获取用户信息

    String getCrewList = "/s-20001";  //获取我的剧目列表

    String changeUserInfo = "/s-10301";  //修改头像或者昵称

    String getFollowsList = "/s-50002";  //获取我的关注列表

    String getVersion = "/p-80101"; //版本

    String getAllCity = "/p-80003"; //获取全部城市

    String getHotCity = "/p-80001"; //获取热门定位

    String myMoka = "/s-10101"; //查看我的模卡

    String changeMoka = "/s-10102"; //修改模卡

    String getCallBoardList = "/p-20101"; //获取通告列表

    String getOperaDetail = "/p-20002"; //获取剧目详情

    String getFaceList = "/s-30001"; //获取面试列表

    String startAfterPlay = "/s-10201"; //开始接戏

    String attentionOpera = "/s-50001"; //关注剧目

    String cancelAttentionOpera = "/s-50003"; //取消关注剧目

    String getAttentionList = "/s-50002"; //获取关注列表

    String cancelFace = "/s-30104"; //取消面试

    String deleteFace = "/s-30103"; //删除面试

    String signFace = "/s-30201"; //签约或拒签

    String uploadFaceVideo = "/s-30101"; //上传试戏视频

    String watchFaceVideo = "/s-30102"; //查看试戏视频

    String sendResume = "/s-30002"; //投递面试

    String syncCity = "/s-80004"; //同步用户选择的城市


    /**
     *
     * 下面为剧组端接口号
     */
    String getRoleList = "/s-20101"; //获取角色列表

    String deleteRole = "/s-20103"; //删除角色

    String enlistRole = "/s-20102"; //招募或关闭角色

    String invitePlay = "/s-30101"; //邀约试戏、签约

    String addRole = "/s-20104"; //添加角色

    String batchRefuseFacer = "/s-30003"; //批量拒绝面试者

    String getOperaFaceList = "/s-20201"; //获取剧目通告列表

    String getActorList = "/s-70001"; //获取艺人列表


//    String myHostUrl = "http://47.111.23.202:6025/apis/client";
    String myHostUrl = "http://47.111.23.202:7123/yifei-apidoc/mock?path=/apis/client";
    String myCrewHostUrl = "http://47.111.23.202:7123/yifei-apidoc/mock?path=/apis/company";

}
