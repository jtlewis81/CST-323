package com.gcu.data;

import java.util.List;
import com.gcu.data.entity.UserEntity;

public interface UserDataAccessInterface
{
	public UserEntity getUserByUsername(String username);
	public String getUsernameByUserId(int userId);
	public List<UserEntity> getAllUsers();
	public boolean add(UserEntity userEntity);
	boolean update(UserEntity userEntity, UserEntity updatedUser);
	public boolean delete(UserEntity userEntity);
}
