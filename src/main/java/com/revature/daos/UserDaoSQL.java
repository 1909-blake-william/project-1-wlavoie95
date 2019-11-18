package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDaoSQL implements UserDao {
	private UserDao userDao = UserDao.currentImplementation;
	private Logger log = Logger.getRootLogger();

	private User extractUser(ResultSet rs) throws SQLException {
		int userId = rs.getInt("ers_users_id");
		String rsUsername = rs.getString("ers_username");
		String rsPassword = rs.getString("ers_password");
		String firstName = rs.getString("user_first_name");
		String lastName = rs.getString("user_last_name");
		String email = rs.getString("user_email");
		int roleId = rs.getInt("user_role_id");
		User u = new User(userId, rsUsername, rsPassword, firstName, lastName, email, roleId);
		System.out.println(u);
		return u;
	}

	/*
	 * This is used for logging in.
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		log.debug("attempting to find user by credentials from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_users " + "WHERE ers_username = ? " 
					+ "AND ers_password = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			System.out.println(username);
			System.out.println(password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User u = extractUser(rs);
				System.out.println(u);
				return u;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
