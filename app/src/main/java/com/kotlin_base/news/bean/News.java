package com.kotlin_base.news.bean;

import java.util.List;

public class News {

    /**
     * dataType : news
     * hasNext : true
     * retcode : 000000
     * appCode : qihoo
     * pageToken : 2
     * data : []
     */

    private String dataType;
    private boolean hasNext;
    private String retcode;
    private String appCode;
    private String pageToken;
    private List<DataBean> data;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{

        /**
         * likeCount : null
         * publishDateStr : 2018-07-06T04:18:00
         * shareCount : null
         * imageUrls : ["http://p6.qhimg.com/t0142e416133a0b3bca.jpg?size=535x350","http://p4.qhimg.com/t01e91d82991171b556.jpg?size=540x356","http://p0.qhimg.com/t0143e3ed438b4e6ebc.jpg?size=544x356"]
         * posterScreenName : 腾讯大辽网
         * content : 2018英雄联盟洲际系列赛亚洲对抗赛在大连体育中心体育馆进行
         * commentCount : null
         * posterId : ln.qq.com
         * publishDate : 1530850680
         * title : 2018英雄联盟洲际赛进行中 大连歹街为LPL赛区打CALL
         * url : http://ln.qq.com/a/20180706/025371.htm
         * id : b6186a3fe0a1f1b542d64832640ef2c4
         * tags : null
         */

        private Object likeCount;
        private String publishDateStr;
        private Object shareCount;
        private String posterScreenName;
        private String content;
        private Object commentCount;
        private String posterId;
        private int publishDate;
        private String title;
        private String url;
        private String id;
        private Object tags;
        private List<String> imageUrls;

        public Object getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Object likeCount) {
            this.likeCount = likeCount;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public void setPublishDateStr(String publishDateStr) {
            this.publishDateStr = publishDateStr;
        }

        public Object getShareCount() {
            return shareCount;
        }

        public void setShareCount(Object shareCount) {
            this.shareCount = shareCount;
        }

        public String getPosterScreenName() {
            return posterScreenName;
        }

        public void setPosterScreenName(String posterScreenName) {
            this.posterScreenName = posterScreenName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Object commentCount) {
            this.commentCount = commentCount;
        }

        public String getPosterId() {
            return posterId;
        }

        public void setPosterId(String posterId) {
            this.posterId = posterId;
        }

        public int getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(int publishDate) {
            this.publishDate = publishDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
}
