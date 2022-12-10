package com.gcu.data;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gcu.data.entity.UserEntity;

@Service
public class UserDataAccessService implements UserDataAccessInterface
{
	@Autowired
	@SuppressWarnings("unused")
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	
	public UserDataAccessService(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public UserEntity getUserByUsername(String username)
	{
		String sql = "SELECT * FROM users WHERE Username = '" + username + "'"; 
		UserEntity user = null;
		try
		{
			SqlRowSet record = jdbcTemplateObject.queryForRowSet(sql);
			if (record.next())
			{
				user = new UserEntity(record.getInt("ID"),
										record.getString("Username"),
										record.getString("Password"),
										record.getString("Email"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return user;
	}

	@Override
	public String getUsernameByUserId(int userId) {
		String sql = "SELECT * FROM users WHERE ID = '" + userId + "'";
		String username = "";
		try
		{
			SqlRowSet record = jdbcTemplateObject.queryForRowSet(sql);
			if (record.next())
			{
				username = record.getString("Username");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return username;
	}
	
	@Override
	public List<UserEntity> getAllUsers()
	{
		String sql = "SELECT * FROM users"; 
		List<UserEntity> users = new ArrayList<UserEntity>();
		try{
			SqlRowSet record = jdbcTemplateObject.queryForRowSet(sql);
			while (record.next()){
				users.add(new UserEntity(
						record.getInt("ID"),
						record.getString("Username"),
						record.getString("Password"),
						record.getString("Email")));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}		
		return users; 
	}

	@Override
	public boolean add(UserEntity userEntity)
	{
		
		String sql = "INSERT INTO users (Username, Password, Email) VALUES (?, ?, ?)";
		try
		{
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(userEntity.getPassword());
			int rows = jdbcTemplateObject.update(
				sql,
				userEntity.getUsername(),
				encodedPassword,
				userEntity.getEmail());

			if (rows == 1)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public boolean update(UserEntity userEntity)
	{
		
		
		return false;
	}

	@Override
	public boolean delete(UserEntity userEntity)
	{
		
		
		return false;
	}
}
