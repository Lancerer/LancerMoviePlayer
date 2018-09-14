package com.example.lancer.MovieMusic.bean;

import java.util.List;

/**
 * author: Lancer
 * date：2018/8/9
 * des:
 * email:tyk790406977@126.com
 */

public class DouYinBean {

    /**
     * error : false
     * results : [{"_id":"5b67b89d9d2122195a5d4275","createdAt":"2018-08-06T10:55:25.717Z","desc":"这会是让他永远后悔的一次点球#陈翔六点半#","publishedAt":"2018-08-06T00:00:00.0Z","source":"web","type":"休息视频","url":"http://v.douyin.com/e9sR9f/","used":true,"who":"lijinshanmx"},{"_id":"5b63cd6b9d21225e0d3f58ca","createdAt":"2018-08-03T11:35:07.252Z","desc":"当家里来美女时\u2026\u2026 ","publishedAt":"2018-08-03T00:00:00.0Z","source":"api","type":"休息视频","url":"http://v.douyin.com/efEmqo/","used":true,"who":"lijinshan"},{"_id":"5b61516b9d212251fdac7881","createdAt":"2018-08-01T14:21:31.966Z","desc":"换了一根香菜好多了🤪","publishedAt":"2018-08-01T00:00:00.0Z","source":"api","type":"休息视频","url":"http://v.douyin.com/eA4GB9/","used":true,"who":"lijinshan"},{"_id":"5b60359b9d2122477951a333","createdAt":"2018-07-31T18:10:35.731Z","desc":"好看的皮囊千篇一律...","publishedAt":"2018-07-31T00:00:00.0Z","source":"api","type":"休息视频","url":"http://v.douyin.com/eBcaVB/","used":true,"who":"lijinshan"},{"_id":"5b5e93919d21220fc5545f49","createdAt":"2018-07-30T12:26:57.671Z","desc":"真不是我吹牛，我能记住所有和她有关的日子，除了今天\u2026\u2026","publishedAt":"2018-07-30T00:00:00.0Z","source":"web","type":"休息视频","url":"http://v.douyin.com/ermxPB/","used":true,"who":"lijinshanmx"},{"_id":"5b501095421aa917a31c0566","createdAt":"2018-07-19T12:16:21.477Z","desc":"月初我吃啥，你吃啥，月底你吃啥我吃啥....","publishedAt":"2018-07-19T00:00:00.0Z","source":"web","type":"休息视频","url":"http://v.douyin.com/JnXFoc/","used":true,"who":"lijinshanmx"},{"_id":"5b4eab04421aa93aa7a19d9c","createdAt":"2018-07-18T10:50:44.83Z","desc":"爱的对话,超级炫 ~. ~","publishedAt":"2018-07-18T00:00:00.0Z","source":"web","type":"休息视频","url":"http://v.douyin.com/JGy5X4/","used":true,"who":"lijinshanmx"},{"_id":"5b481d29421aa90bb41cc621","createdAt":"2018-07-13T11:31:53.780Z","desc":"这可能是@阿坚最想让你看到的一条抖音了！..","publishedAt":"2018-07-13T00:00:00.0Z","source":"web","type":"休息视频","url":"http://v.douyin.com/JQNK2U/","used":true,"who":"lijinshanmx"},{"_id":"5b45701b421aa92fccb520b1","createdAt":"2018-07-11T10:48:59.691Z","desc":"我老丈母从我家走时就这样。。。。。","publishedAt":"2018-07-11T00:00:00.0Z","source":"web","type":"休息视频","url":"https://v.douyin.com/JuqroL/","used":true,"who":"lijinshanmx"},{"_id":"5b441f26421aa92fccb520a3","createdAt":"2018-07-10T10:51:18.865Z","desc":"我是很认真和正经的与你合拍,毫无违和感。。。","publishedAt":"2018-07-10T00:00:00.0Z","source":"web","type":"休息视频","url":"http://v.douyin.com/JaymEo/","used":true,"who":"lijinshanmx"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5b67b89d9d2122195a5d4275
         * createdAt : 2018-08-06T10:55:25.717Z
         * desc : 这会是让他永远后悔的一次点球#陈翔六点半#
         * publishedAt : 2018-08-06T00:00:00.0Z
         * source : web
         * type : 休息视频
         * url : http://v.douyin.com/e9sR9f/
         * used : true
         * who : lijinshanmx
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
