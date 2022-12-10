package com.gcu.data;

import java.util.List;
import com.gcu.data.entity.UserEntity;

public interface UserDataAccessInterface
{
	public UserEntity getUserByUsername(String username);
	public String getUsernameByUserId(int userId);
	public List<UserEntity> getAllUsers();
	public boolean add(UserEntity userEntity);
	public boolean update(UserEntity userEntity);
	public boolean delete(UserEntity userEntity);
}
