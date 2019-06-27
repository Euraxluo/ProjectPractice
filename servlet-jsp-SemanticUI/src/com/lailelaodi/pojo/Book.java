package com.lailelaodi.pojo;

import java.util.Date;

/**
 * @ClassName Book
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-19 上午11:02
 */
public class Book {
    private int bid;
    private String origintitle;
    private String author;
    private String translator;
    private String title;
    private String subtitle;
    private String summary;
    private String publisher;
    private String pubdate;
    private String image;
    private int numbers;
    private Date createtime;
    private Date updatetime;

    public Book() {
        super();
    }
    public Book(int bid, String origintitle, String author, String translator, String title, String subtitle, String summary, String publisher, String pubdate, String image, int numbers, Date createtime, Date updatetime) {
        this.bid = bid;
        this.origintitle = origintitle;
        this.author = author;
        this.translator = translator;
        this.title = title;
        this.subtitle = subtitle;
        this.summary = summary;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.image = image;
        this.numbers = numbers;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setOrigintitle(String origintitle) {
        this.origintitle = origintitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getBid() {
        return bid;
    }

    public String getOrigintitle() {
        return origintitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getTranslator() {
        return translator;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getSummary() {
        return summary;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getImage() {
        return image;
    }

    public int getNumbers() {
        return numbers;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }
}
