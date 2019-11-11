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

	private Logger log = Logger.getRootLogger();

	private User extractUser(ResultSet rs) throws SQLException {
		int id = rs.getInt("ers_user_id");
		String rsUsername = rs.getString("ers_username");
		// String rsPassword = rs.getString("ers_password");
		String firstName = rs.getString("user_first_name");
		String lastName = rs.getString("user_last_name");
		String email = rs.getString("user_email");
		int roleId = rs.getInt("user_role_id");
		String role;
		if (roleId == 1) {
			role = "Apprentice";
		} else {
			role = "Wizard";
		}
		
		return new User(roleId, rsUsername, "", firstName, lastName, email, role);
	}

	/*
	 * This is used for logging in.
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		log.debug("attempting to find user by credentials from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM pokemon_users " + "WHERE username = ? AND password = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractUser(rs);
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}

	}
}
