package com.yidao.module_lib.base.ievent.video;

import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseEvent;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IVideoListEvent extends IBaseEvent {

    String VideoListEvent = "videoList";
    String PraiseEvent = "praise";
    String CommentListEvent = "commentList";
    String CommentReplyEvent = "commentReply";

    void onResponse(String type, ResponseBean responseBean, boolean success);
}
