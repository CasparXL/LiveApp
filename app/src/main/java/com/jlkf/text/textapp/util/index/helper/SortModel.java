package com.jlkf.text.textapp.util.index.helper;
 
public class SortModel {
 
	private int id;   //显示的数据
	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}