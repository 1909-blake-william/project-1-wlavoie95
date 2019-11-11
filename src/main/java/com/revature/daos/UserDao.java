package com.revature.daos;

import com.revature.models.User;

public interface UserDao {
	UserDao userDao = new UserDaoSQL();
	
	User findByUsernameAndPassword(String username, String password);
}
