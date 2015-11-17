package com.ams.app.entities;

public class ArticleDto {
	
	private int id;
	private String title;
	private int userid;
	private String uername;
	private String content;
	private String pubdate;
	private boolean enable;
	private String image;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdaye) {
		this.pubdate = pubdaye;
	}
	public boolean getEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getUername() {
		return uername;
	}
	public void setUername(String uername) {
		this.uername = uername;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", userid=" + userid
				+ ", content=" + content + ", pubdaye=" + pubdate + ", enable="
				+ enable + ", image=" + image + "]";
	}
	
}