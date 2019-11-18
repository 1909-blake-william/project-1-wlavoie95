package com.revature.daos;

import com.revature.models.User;

public interface UserDao {
	UserDao currentImplementation = new UserDaoSQL();
	
	User findByUsernameAndPassword(String username, String password);
}
