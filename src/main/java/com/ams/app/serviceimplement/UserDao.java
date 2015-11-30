package com.ams.app.serviceimplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.ams.app.entities.UserDto;
import com.ams.app.services.UserService;


public class UserDao implements UserService {
	private DataSource dataSource;
	private Connection con;
	
	public UserDao(DataSource dataSource2) {
		this.dataSource = dataSource2;
	}
	
	@Override
	public ArrayList<UserDto> list(int limitrow, int page) {
		if(page<=0) page=1;
		int offset = limitrow * page - limitrow;
		UserDto user = null;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT * FROM tbuser ORDER BY id LIMIT ? OFFSET ?"; //where enable = true
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, limitrow);
			ps.setInt(2, offset);
			ResultSet rs = ps.executeQuery();
			ArrayList<UserDto> users = new ArrayList<UserDto>();
			while (rs.next()) {
				user = new UserDto();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEnable(rs.getBoolean(4));
				user.setEmail(rs.getString(5));
				user.setAddress(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setName(rs.getString(8));
				user.setGender(rs.getString(9));
				user.setImage(rs.getString(10));

				users.add(user);
			}
			rs.close();
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean insertUser(UserDto user) {
		System.out.println("user dao."+user.getName());
		
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO tbuser(username,password,enable,email,address,phone,name,gender,image) "
					+ "VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setBoolean(3, user.isEnable());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getAddress());
			ps.setString(6, user.getPhone());
			ps.setString(7, user.getName());
			ps.setString(8, user.getGender());
			if (user.getImage() != null) {
				ps.setString(9, user.getImage());
			} else {
				ps.setString(9, "default.jpg");
			}
			if (ps.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updateUser(UserDto user) {
		//System.out.println("user enable "+user.isEnable());
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE tbuser SET username=? , password=? ," //enable = ?,"
					+ "email=?, address=?, phone=?, name=?,"
					+ "gender=?, image=? WHERE id = ?";// and enable = true";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			//ps.setBoolean(3, user.isEnable());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getAddress());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getName());
			ps.setString(7, user.getGender());
			ps.setString(8, user.getImage());
			ps.setInt(9, user.getId());
			if (ps.executeUpdate() > 0) {
				System.out.println("success with updated");
				//con.commit();
				return true;
			}
			System.out.println("fail with updated");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public UserDto getUser(int id) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT * FROM tbuser WHERE id=? Limit 1"; //and enable = true 
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			UserDto user = null;
			if (rs.next()) {
				user = new UserDto();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEnable(rs.getBoolean(4));
				user.setEmail(rs.getString(5));
				user.setAddress(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setName(rs.getString(8));
				user.setGender(rs.getString(9));
				user.setImage(rs.getString(10));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<UserDto> getPagination(int page, int limit) {
		UserDto user = null;
		int begin;
		begin = (page * limit) - limit;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT * FROM tbuser where enable = true OFFSET " + begin + " LIMIT "
					+ limit;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ArrayList<UserDto> users = new ArrayList<UserDto>();
			while (rs.next()) {
				user = new UserDto();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEnable(rs.getBoolean(4));
				user.setEmail(rs.getString(5));
				user.setAddress(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setName(rs.getString(8));
				user.setGender(rs.getString(9));
				user.setImage(rs.getString(10));

				users.add(user);
			}
			rs.close();
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int getTotalPage(int limit) {
		int total_page;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(id) FROM tbuser"; //where enable = true
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total_page = rs.getInt(1) / limit;
			if(rs.getInt(1)%limit!=0) total_page+=1;
			System.out.println("total page "+total_page);
			rs.close();
			return total_page;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public UserDto showUser(String usrName) {
		try {
			con = dataSource.getConnection();
			String sql = "SELECT * FROM tbuser WHERE username=? and enable = true Limit 1";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usrName);
			ResultSet rs = ps.executeQuery();
			UserDto user = null;
			if (rs.next()) {
				user = new UserDto();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEnable(rs.getBoolean(4));
				user.setEmail(rs.getString(5));
				user.setAddress(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setName(rs.getString(8));
				user.setGender(rs.getString(9));
				user.setImage(rs.getString(10));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
		
	@Override
	public ArrayList<UserDto> search(String columnName, String keyword, int limitrow, int page) {
		if(page<=0) page=1;
		int offset = limitrow * page - limitrow;
		ArrayList<UserDto> list = new ArrayList<>();
		UserDto user = null;
		String sql = "SELECT id,username,password,enable,email,address,phone,name,gender,image"
				+ " FROM tbuser"
				+ " WHERE Lower(" + columnName + ") LIKE ? AND enable = true"
				+ " LIMIT ? OFFSET ?";
		try (Connection conn = dataSource.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, "%" + keyword.toLowerCase() + "%");
			ps.setInt(2, limitrow);
			ps.setInt(3, offset);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps);
			while (rs.next()) {
				user = new UserDto();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setEnable(rs.getBoolean(4));
				user.setEmail(rs.getString(5));
				user.setAddress(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setName(rs.getString(8));
				user.setGender(rs.getString(9));
				user.setImage(rs.getString(10));
				list.add(user);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	@Override
	public int getLastID() {
		int id = 0;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT MAX(id) FROM tbuser where enable = true";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id=rs.getInt(1);
				System.out.println(id);
			}
			rs.close();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean toggle(int artId) {
		String sql = "UPDATE tbuser SET enable = not enable WHERE id = ?";
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
