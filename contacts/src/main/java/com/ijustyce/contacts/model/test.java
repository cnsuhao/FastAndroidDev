package com.ijustyce.contacts.model;

import java.util.List;

/**
 * Created by yangchun on 16/4/6.
 */
public class test {


    /**
     * result : 1
     * error : 操作成功！
     * album : {"albumId":1033,"backImg":"/app/20160406/ab/6a511944-d3f3-405a-8b9d-aac9de037792.png?1459923890762","albumTitle":"每日1号扬州能吃的鹅蛋粉啊","mainImg":"/app/20160406/ab/9edd4325-90d6-43c4-bbeb-8523fe5a5ff8.jpeg?1459923815316","shareImg":"/app/20160406/ab/2b737b27-3b78-49b8-8ea3-de3e6ddecb14.png?1459923905658","shareTitle":"测试","albumSubTitle":"香粉世家始于1980年，天下香粉莫如扬州，扬州香粉，莫如谢馥春。"}
     * details : [{"albumDetailId":1085,"type":1,"imgWidth":658,"imgHeight":437,"title":"11","imgUrl":"/app/20160406/ab/lzha67de3bf-d93e-48d0-a774-5c6a9da4dc9e.jpeg","content":"11","links":[{"x":33,"y":49,"linkType":2,"linkId":"150","albumDetailId":1085,"imgUrl":"/app/20151218/bd/lzhe7facb4c-9650-49f3-92f3-81eb3d07a57d.png","linkDesc":"111"}]},{"albumDetailId":1086,"type":2,"title":"111","linkType":3,"linkId":"1","imgUrl":"/app/20160314/cd/08a9f056-d7a6-43c6-ae25-cb377b53243f.png?1457936499979","price":11.25},{"albumDetailId":1087,"type":2,"title":"11213","linkType":4,"linkId":"3","imgUrl":"/app/20151130/cf/lzh3aac5824-fb45-4e49-8147-0463a857033a.png","price":3},{"albumDetailId":1088,"type":3,"content":"123123123","title":"123123213","isShow":1},{"albumDetailId":1089,"type":4,"imgWidth":700,"imgHeight":250,"imgUrl":"/app/20160406/ab/5b5baf44-4bb7-421c-8579-59a32cae8f6e.png?1459924707137","vedioUrl":"http://k.youku.com/player/getFlvPath/sid/645992455762012a4b692_00/st/flv/fileid/030002100050F980E6E71E05CF07DD89F6E8F6-0EBA-D115-B5C5-2153AF096313?K=a8220c6a2169dba0282b50d6&ctype=12&ev=1&token=2904&oip=3549804647&ep=dCaRE0CPUssF4iXdiD8bNiiwcn4NXP4J9h%2BFg9NiALshSJ62kT6kwei3T4pCE4pvciVyZOP43daSYjYSYfA22xkQq0%2BpPPqS%2B4Pm5a9SwZNxEBk%2FdM7Wt1ScSjD1&ymovie=1"}]
     * albumSummary : {"comment":0,"collect":0}
     * comments : null
     */

    private int result;
    private String error;
    /**
     * albumId : 1033
     * backImg : /app/20160406/ab/6a511944-d3f3-405a-8b9d-aac9de037792.png?1459923890762
     * albumTitle : 每日1号扬州能吃的鹅蛋粉啊
     * mainImg : /app/20160406/ab/9edd4325-90d6-43c4-bbeb-8523fe5a5ff8.jpeg?1459923815316
     * shareImg : /app/20160406/ab/2b737b27-3b78-49b8-8ea3-de3e6ddecb14.png?1459923905658
     * shareTitle : 测试
     * albumSubTitle : 香粉世家始于1980年，天下香粉莫如扬州，扬州香粉，莫如谢馥春。
     */

    private AlbumEntity album;
    /**
     * comment : 0
     * collect : 0
     */

    private AlbumSummaryEntity albumSummary;
    private Object comments;
    /**
     * albumDetailId : 1085
     * type : 1
     * imgWidth : 658
     * imgHeight : 437
     * title : 11
     * imgUrl : /app/20160406/ab/lzha67de3bf-d93e-48d0-a774-5c6a9da4dc9e.jpeg
     * content : 11
     * links : [{"x":33,"y":49,"linkType":2,"linkId":"150","albumDetailId":1085,"imgUrl":"/app/20151218/bd/lzhe7facb4c-9650-49f3-92f3-81eb3d07a57d.png","linkDesc":"111"}]
     */

    private List<DetailsEntity> details;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public AlbumSummaryEntity getAlbumSummary() {
        return albumSummary;
    }

    public void setAlbumSummary(AlbumSummaryEntity albumSummary) {
        this.albumSummary = albumSummary;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public List<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsEntity> details) {
        this.details = details;
    }

    public static class AlbumEntity {
        private int albumId;
        private String backImg;
        private String albumTitle;
        private String mainImg;
        private String shareImg;
        private String shareTitle;
        private String albumSubTitle;

        public int getAlbumId() {
            return albumId;
        }

        public void setAlbumId(int albumId) {
            this.albumId = albumId;
        }

        public String getBackImg() {
            return backImg;
        }

        public void setBackImg(String backImg) {
            this.backImg = backImg;
        }

        public String getAlbumTitle() {
            return albumTitle;
        }

        public void setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getAlbumSubTitle() {
            return albumSubTitle;
        }

        public void setAlbumSubTitle(String albumSubTitle) {
            this.albumSubTitle = albumSubTitle;
        }
    }

    public static class AlbumSummaryEntity {
        private int comment;
        private int collect;

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }
    }

    public static class DetailsEntity {
        private int albumDetailId;
        private int type;
        private int imgWidth;
        private int imgHeight;
        private String title;
        private String imgUrl;
        private String content;
        /**
         * x : 33
         * y : 49
         * linkType : 2
         * linkId : 150
         * albumDetailId : 1085
         * imgUrl : /app/20151218/bd/lzhe7facb4c-9650-49f3-92f3-81eb3d07a57d.png
         * linkDesc : 111
         */

        private List<LinksEntity> links;

        public int getAlbumDetailId() {
            return albumDetailId;
        }

        public void setAlbumDetailId(int albumDetailId) {
            this.albumDetailId = albumDetailId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getImgWidth() {
            return imgWidth;
        }

        public void setImgWidth(int imgWidth) {
            this.imgWidth = imgWidth;
        }

        public int getImgHeight() {
            return imgHeight;
        }

        public void setImgHeight(int imgHeight) {
            this.imgHeight = imgHeight;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<LinksEntity> getLinks() {
            return links;
        }

        public void setLinks(List<LinksEntity> links) {
            this.links = links;
        }

        public static class LinksEntity {
            private int x;
            private int y;
            private int linkType;
            private String linkId;
            private int albumDetailId;
            private String imgUrl;
            private String linkDesc;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getLinkType() {
                return linkType;
            }

            public void setLinkType(int linkType) {
                this.linkType = linkType;
            }

            public String getLinkId() {
                return linkId;
            }

            public void setLinkId(String linkId) {
                this.linkId = linkId;
            }

            public int getAlbumDetailId() {
                return albumDetailId;
            }

            public void setAlbumDetailId(int albumDetailId) {
                this.albumDetailId = albumDetailId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLinkDesc() {
                return linkDesc;
            }

            public void setLinkDesc(String linkDesc) {
                this.linkDesc = linkDesc;
            }
        }
    }
}
