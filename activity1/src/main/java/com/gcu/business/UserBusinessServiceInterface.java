package com.gcu.business;

import java.util.List;
import com.gcu.data.entity.UserEntity;

public interface UserBusinessServiceInterface
{
	public List<UserEntity> getAllUsers();
	public UserEntity getUserByUsername(String username);
	public boolean addUser(UserEntity userEntity);
	boolean updateUser(UserEntity userEntity, UserEntity updatedUser);
	public boolean deleteUser(UserEntity userEntity);
}
