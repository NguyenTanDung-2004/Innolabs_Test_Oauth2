package com.example.Mini_Project1.service;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Mini_Project1.entity.User;
import com.example.Mini_Project1.exception.ExceptionCode;
import com.example.Mini_Project1.exception.UserException;
import com.example.Mini_Project1.mapper.UserMapper;
import com.example.Mini_Project1.repository.UserRepository;
import com.example.Mini_Project1.request.CreateStaffRequest;
import com.example.Mini_Project1.request.LoginRequest;
import com.example.Mini_Project1.request.SignUpRequest;
import com.example.Mini_Project1.request.UpdatePasswordRequest;
import com.example.Mini_Project1.response.Response;
import com.example.Mini_Project1.response.ResponseCode;
import com.example.Mini_Project1.response.UserResponse;
import com.example.Mini_Project1.utils.JwtTokenUtils;
import com.example.Mini_Project1.utils.MailUtils;
import com.example.Mini_Project1.utils.PasswordUtils;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	private MailUtils mailUtils;

	public ResponseEntity signUp(SignUpRequest signUpRequest) {
		// check email
		User user = this.userRepository.getUserByEmail(signUpRequest.getEmail());
		if (user == null) {
			// convert
			User convertedUser = this.userMapper.signUpRequestToUser(signUpRequest);
			// set role
			convertedUser.setRole("user");
			// set password
			convertedUser.setPassword(PasswordUtils.encryptPassword(signUpRequest.getPassword()));
			// save
			this.userRepository.save(convertedUser);
			// return
			return ResponseEntity.ok().body(ResponseCode.SignUpSuccessfully.responseCodeToJson());
		} else {
			// throw exception
			throw new UserException(ExceptionCode.EmailAlreadyExists);
		}
	}

	public ResponseEntity login(LoginRequest loginRequest) {
		// check email
		User user = this.userRepository.getUserByEmail(loginRequest.getEmail());
		if (user == null) {
			// throw exception
			throw new UserException(ExceptionCode.EmailDoesNotExist);
		} else {
			// check password
			if (PasswordUtils.checkPassword(loginRequest.getPassword(), user.getPassword()) == 1) {
				// create token
				String token = this.jwtTokenUtils.createToken(user);
				// create response
				Response response = new Response(ResponseCode.LoginSuccessfully.responseCodeToJson(), token);
				// return
				return ResponseEntity.ok().body(response);
			} else {
				// password wrong
				throw new UserException(ExceptionCode.WrongPassword);
			}
		}
	}

	public ResponseEntity getTokenResetPassword(String email) {
		// check email
		User user = this.userRepository.getUserByEmail(email);
		if (user == null) {
			// throw exception
			throw new UserException(ExceptionCode.EmailDoesNotExist);
		} else {
			String token = mailUtils.createToken();
			mailUtils.setRecipient(email);
			mailUtils.setSubject("TOKEN RESET PASSWORD!");
			mailUtils.setMsgBody(mailUtils.createAccountBody(token));
			mailUtils.sendHtmlEmail();
			// update user
			user.setCurrentToken(token);
			// save
			userRepository.save(user);
			// return
			return ResponseEntity.ok().body(ResponseCode.CheckEmail.responseCodeToJson());
		}
	}

	public ResponseEntity updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		// check email
		User user = this.userRepository.getUserByEmail(updatePasswordRequest.getEmail());
		if (user == null) {
			// throw exception
			throw new UserException(ExceptionCode.EmailDoesNotExist);
		} else {
			// check currentToken
			if (user.getCurrentToken() == null) {
				throw new UserException(ExceptionCode.WrongToken);
			}
			if (user.getCurrentToken().equals(updatePasswordRequest.getToken())) {
				// update password
				user.setPassword(PasswordUtils.encryptPassword(updatePasswordRequest.getNewPassword()));
				// delete token
				user.setCurrentToken(null);
				// save
				userRepository.save(user);
				// return
				return ResponseEntity.ok().body(ResponseCode.UpdatePassword.responseCodeToJson());
			} else {
				throw new UserException(ExceptionCode.WrongToken);
			}
		}
	}

	public ResponseEntity createStaff(CreateStaffRequest createStaffRequest) {
		// check email
		User user = this.userRepository.getUserByEmail(createStaffRequest.getEmail());
		if (user == null) {
			// convert
			User convertedUser = this.userMapper.createStaffToUser(createStaffRequest);
			// set role
			convertedUser.setRole("staff");
			// set password
			convertedUser.setPassword(PasswordUtils.encryptPassword(createStaffRequest.getPassword()));
			// save
			this.userRepository.save(convertedUser);
			// return
			return ResponseEntity.ok().body(ResponseCode.CreateStaff.responseCodeToJson());
		} else {
			// throw exception
			throw new UserException(ExceptionCode.EmailAlreadyExists);
		}

	}

	public ResponseEntity getAllStaff() {
		// get staff from database
		List<User> listStaffs = this.userRepository.getAllStaff();
		// create response
		List<UserResponse> listResponseStaffs = new ArrayList<>();
		for (int i = 0; i < listStaffs.size(); i++) {
			// convert
			UserResponse responseStaff = this.userMapper.UserToResponseUser(listStaffs.get(i));
			// add
			listResponseStaffs.add(responseStaff);
		}
		// return
		return ResponseEntity.ok().body(listResponseStaffs);
	}

	public ResponseEntity deleteStaff(String staffId) {
		// check staff
		Optional<User> optional = this.userRepository.findById(staffId);
		if (optional.isEmpty()) {
			// throw exception
			throw new UserException(ExceptionCode.IdDoesNotExist);
		} else {
			User user = optional.get();
			// check role staff
			if (user.getRole().equals("staff") == false) {
				// throw exception
				throw new UserException(ExceptionCode.IdDoesNotExist);
			} else {
				// delete
				user.setFlagDelete(1);
				this.userRepository.save(user);
				// return
				return ResponseEntity.ok().body(ResponseCode.DeleteStaff.responseCodeToJson());
			}
		}
	}

	public ResponseEntity getUserInfo(String userId) {
		// check user
		Optional<User> optional = this.userRepository.findById(userId);
		if (optional.isEmpty()) {
			// throw exception
			throw new UserException(ExceptionCode.IdDoesNotExist);
		} else {
			User user = optional.get();
			// convert
			UserResponse userResponse = this.userMapper.UserToResponseUser(user);
			return ResponseEntity.ok().body(userResponse);
		}
	}
}
