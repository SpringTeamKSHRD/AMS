package com.ams.app.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.ams.app.entities.ArticleDto;

@Service
public interface ArticleService {
	public ArrayList<ArticleDto> list();
	public boolean add(ArticleDto art);
	public boolean update(ArticleDto art);
	public boolean delete(int artId);
	public ArticleDto show(int artId);
	public ArrayList<ArticleDto> search(String keyword, String type);
}
