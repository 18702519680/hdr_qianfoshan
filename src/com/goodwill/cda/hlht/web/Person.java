package com.goodwill.cda.hlht.web;

public class Person {
	private String pid;
	private String vid;

	public Person() {
		super();
	}

	public Person(String pid, String vid) {
		super();
		this.pid = pid;
		this.vid = vid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

}
