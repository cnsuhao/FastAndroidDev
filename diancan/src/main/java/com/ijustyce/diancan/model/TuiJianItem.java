package com.ijustyce.diancan.model;

import java.io.Serializable;

/**
 * Created by yc on 16-4-29.    推荐model
 */
public class TuiJianItem implements Serializable {

    private String id;
    private String title;
    private String long_title;
    private String source;
    private String link;
    private String pic;
    private String kpic;
    private String intro;
    private int pubDate;
    private String comments;
    private String category;
    private String feedShowStyle;
    private int comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getKpic() {
        return kpic;
    }

    public void setKpic(String kpic) {
        this.kpic = kpic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getPubDate() {
        return pubDate;
    }

    public void setPubDate(int pubDate) {
        this.pubDate = pubDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFeedShowStyle() {
        return feedShowStyle;
    }

    public void setFeedShowStyle(String feedShowStyle) {
        this.feedShowStyle = feedShowStyle;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
