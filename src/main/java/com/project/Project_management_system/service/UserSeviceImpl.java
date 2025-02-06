package com.project.Project_management_system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Project_management_system.config.JwtProvider;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.repository.UserRepository;

@Service
public class UserSeviceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User findUserProfileByJwt(String jwt) throws Exception {
		// TODO Auto-generated method stub
		String email = JwtProvider.getEmailFromToken(jwt);
		return findUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		User user= userRepository.findByEmail(email);
		if(user == null) {
			throw new Exception("user not found");
		}
		return user;
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) {
			throw new Exception("user not found");
		}
		return optionalUser.get();
	}

	@Override
	public User updateUsersProjectSize(User user, int number) {
		// TODO Auto-generated method stub
		user.setProjectSize(user.getProjectSize()+number);
	
		return userRepository.save(user);
	}

	
}
