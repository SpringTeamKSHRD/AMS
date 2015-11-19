package com.ams.app.serviceimplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ams.app.entities.ArticleDto;
import com.ams.app.services.ArticleService;


public class ArticleDao implements ArticleService {

	@Autowired
	private DataSource dataSource;

	public ArticleDao(DataSource dataSource2) {
		this.dataSource = dataSource2;
	}

	public ArrayList<ArticleDto> list(int limitrow, int page) {
		ArrayList<ArticleDto> arr = new ArrayList<ArticleDto>();
		ArticleDto a = null;
		if(page==0) page=1;
		int offset = limitrow * page - limitrow;
		String sql = "SELECT tbarticle. ID, tbarticle.title, tbarticle.publish_date, tbarticle. ENABLE, tbarticle.image, tbarticle. CONTENT, tbarticle.userid, tbuser. NAME FROM ( tbarticle JOIN tbuser ON ((tbarticle.userid = tbuser. ID))) LIMIT ? OFFSET ?";
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);		
		)
		{
			ps.setInt(1, limitrow);
			ps.setInt(2, offset);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps);
			while(rs.next()){
				a = new ArticleDto();
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setEnable(rs.getBoolean("enable"));
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setImage(rs.getString("image"));
				a.setUserid(rs.getInt("userid"));
				a.setPdate(rs.getDate("publish_date"));
				arr.add(a);
			}
			return arr;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public boolean add(ArticleDto art) {
		String sql = "INSERT INTO tbarticle(title,userid,content,publish_date,enable,image) VALUES(?,?,?,NOW(),?,?)";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, art.getTitle());
			ps.setInt(2, art.getUserid());
			ps.setString(3, art.getContent());
			ps.setBoolean(4, art.getEnable());
			ps.setString(5, art.getImage());
			if (ps.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean update(ArticleDto art) {
		String sql = "UPDATE tbarticle set title=?,userid=?,content=?,publish_date=?,enable=?,image=? WHERE id =?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setString(1, art.getTitle());
			ps.setInt(2, art.getUserid());
			ps.setString(2, art.getContent());
			ps.setBoolean(4, art.getEnable());
			ps.setString(5, art.getImage());
			if (ps.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean delete(int artId) {
		String sql = "DELETE FROM tbarticle WHERE id =?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, artId);
			if (ps.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public ArrayList<ArticleDto> show(int artId) {
		ArticleDto a = null;
		ArrayList<ArticleDto> arr = new ArrayList<ArticleDto>();
		String sql = "SELECT tbarticle. ID, tbarticle.title, tbarticle.publish_date, tbarticle. ENABLE, tbarticle.image, tbarticle. CONTENT, tbarticle.userid, tbuser. NAME FROM ( tbarticle JOIN tbuser ON ((tbarticle.userid = tbuser. ID))) WHERE tbarticle. ID = ?";
		try (Connection cnn = dataSource.getConnection(); PreparedStatement ps = cnn.prepareStatement(sql);

		) {
			ps.setInt(1, artId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				a = new ArticleDto();
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setEnable(rs.getBoolean("enable"));
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setImage(rs.getString("image"));
				a.setUserid(rs.getInt("userid"));
				a.setPdate(rs.getDate("publish_date"));
				arr.add(a);
			}
			return arr;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public ArrayList<ArticleDto> search(String columnName, String keyword, int limitrow, int page) {
		int offset = limitrow * page - limitrow;
		ArrayList<ArticleDto> list = new ArrayList<>();
		ArticleDto s = null;
		String sql = "SELECT id,title,name,publish_date,userid,content,enable,image"
				+ " FROM v_list_all_article"
				+ " WHERE Lower(" + columnName + ")"
				+ " LIKE ? LIMIT ? OFFSET ?";
		try (Connection conn = dataSource.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, "%" + keyword.toLowerCase() + "%");
			ps.setInt(2, limitrow);
			ps.setInt(3, offset);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps);
			while (rs.next()) {
				s = new ArticleDto();
				s.setId(rs.getInt("id"));
				s.setTitle(rs.getString("title"));
				s.setUserid(rs.getInt("userid"));
				s.setName(rs.getString("name"));
				s.setPdate(rs.getDate("publish_date"));
				s.setEnable(rs.getBoolean("ENABLE"));
				s.setImage(rs.getString("image"));
				list.add(s);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	public ArrayList<ArticleDto> listByUser(int userid, int limitrow, int page) {
		int offset = limitrow * page - limitrow;
		ArrayList<ArticleDto> list = new ArrayList<>();
		ArticleDto s = new ArticleDto();
		String sql = "SELECT id,title,name,publish_date,userid,content,enable,image"
				+ " FROM v_list_all_article"
				+ " WHERE userid = ?"
				+ " LIMIT ? OFFSET ?";
		try (Connection conn = dataSource.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, userid);
			ps.setInt(2, limitrow);
			ps.setInt(3, offset);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps);
			while (rs.next()) {
				s.setId(rs.getInt("id"));
				s.setTitle(rs.getString("title"));
				s.setUserid(rs.getInt("userid"));
				s.setName(rs.getString("name"));
				s.setPdate(rs.getDate("publish_date"));
				s.setEnable(rs.getBoolean("ENABLE"));
				s.setImage(rs.getString("image"));
				list.add(s);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	@Override
	public boolean toggle(int artId) {		
		String sql = "UPDATE tbarticle SET enable = not enable WHERE id = ?";
		try (Connection cnn = dataSource.getConnection(); 
				PreparedStatement ps = cnn.prepareStatement(sql);) {
			ps.setInt(1, artId);
			if (ps.executeUpdate() > 0){
				System.out.println(ps);
				return true; //update successfully //not the state of article
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



}
