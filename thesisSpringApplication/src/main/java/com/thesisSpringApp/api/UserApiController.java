package com.thesisSpringApp.api;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thesisSpringApp.Dto.CommitteeDetailDTO;
import com.thesisSpringApp.Dto.CommitteeUserDetailDTO;
import com.thesisSpringApp.Dto.CurrentUserDetailDto;
import com.thesisSpringApp.Dto.ForgetPasswordDTO;
import com.thesisSpringApp.Dto.OtpDTO;
import com.thesisSpringApp.Dto.PasswordDTO;
import com.thesisSpringApp.Dto.UserDTO;
import com.thesisSpringApp.Dto.UserListsByRoleDTO;
import com.thesisSpringApp.Dto.UserLoginDto;
import com.thesisSpringApp.Dto.UsernameDTO;
import com.thesisSpringApp.Dto.UsersDTO;
import com.thesisSpringApp.JwtComponents.JwtService;
import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisCommitteeRate;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.CommitteeUserService;
import com.thesisSpringApp.service.OtpService;
import com.thesisSpringApp.service.RoleService;
import com.thesisSpringApp.service.ScoreService;
import com.thesisSpringApp.service.ThesisCommitteeRateService;
import com.thesisSpringApp.service.ThesisUserService;
import com.thesisSpringApp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private ThesisUserService thesisUserService;
	private JwtService jwtService;
    private CommitteeUserService committeeUserService;
    private ThesisCommitteeRateService thesisCommitteeRateService;
    private ScoreService scoreService;
    private OtpService otpService;

    @Autowired
    public UserApiController(UserService userService, PasswordEncoder passwordEncoder,
                             RoleService roleService, ThesisUserService thesisUserService,
                             CommitteeUserService committeeUserService, JwtService jwtService,
                             ThesisCommitteeRateService thesisCommitteeRateService,
                             ScoreService scoreService, OtpService otpService) {

        super();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.thesisUserService = thesisUserService;
		this.jwtService = jwtService;
        this.committeeUserService = committeeUserService;
        this.thesisCommitteeRateService = thesisCommitteeRateService;
        this.scoreService = scoreService;
        this.otpService = otpService;
    }

	@PostMapping("/login/")
	@CrossOrigin
	public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
		if (this.userService.authUser(userLoginDto.getUsername(),
				userLoginDto.getPassword())) {
			String token = jwtService.generateTokenLogin(userLoginDto.getUsername(),
					userLoginDto.getPassword());

			return new ResponseEntity<>(token, HttpStatus.OK);
		}

		return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "/current-user/", produces = {
			MediaType.APPLICATION_JSON_VALUE
	})
	@CrossOrigin
	public ResponseEntity<CurrentUserDetailDto> getCurrentUserApi() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			User user =  userService.getUserByUsername((authentication.getName()));
			CurrentUserDetailDto c = new CurrentUserDetailDto(user, user.getFacultyId(),
					user.getRoleId());
			return new ResponseEntity<CurrentUserDetailDto>(c, HttpStatus.OK);
		}
		return null;
	}


    @GetMapping("/")
    public ResponseEntity<UsersDTO> getUsers() {
        List<User> users = userService.getAllUsers();


        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setAcademicManagers(new ArrayList<>());
        usersDTO.setLecturers(new ArrayList<>());
        usersDTO.setStudents(new ArrayList<>());
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFullName(user.getLastName() + " " + user.getFirstName());
            userDTO.setUserUniversityId(user.getUseruniversityid());
            userDTO.setAvatar(user.getAvatar());

            switch (user.getRoleId().getName()) {
                case "ROLE_GIAOVU":
                    usersDTO.getAcademicManagers().add(userDTO);
                    break;
                case "ROLE_GIANGVIEN":
                    usersDTO.getLecturers().add(userDTO);
                    break;
                case "ROLE_SINHVIEN":
                    usersDTO.getStudents().add(userDTO);
                    break;
            }
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/role/get2RoleList/")
    @CrossOrigin
    public ResponseEntity<UserListsByRoleDTO> getUsersByRoleNameApi() {
        Role role1 = roleService.getRoleByName("ROLE_GIANGVIEN");
        Role role2 = roleService.getRoleByName("ROLE_SINHVIEN");

        UserListsByRoleDTO dto = new UserListsByRoleDTO();
        dto.setUsersGiangVien(userService.getUserByRoleName(role1));
        dto.setUsersSinhVien(userService.getUserByRoleName(role2));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

	@GetMapping("/lecturers/")
    @CrossOrigin
    public ResponseEntity<List<User>> getLecturers() {
        Role role = roleService.getRoleByName("ROLE_GIANGVIEN");

        List<User> users = userService.getUserByRoleName(role);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/students/")
    @CrossOrigin
    public ResponseEntity<List<User>> getStudents() {
        Role role = roleService.getRoleByName("ROLE_SINHVIEN");

        List<User> users = userService.getUserByRoleName(role);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/students/noneThesis/")
    public ResponseEntity<List<User>> getStudentsNoneThesis() {
        Role role = roleService.getRoleByName("ROLE_SINHVIEN");

        List<User> users = userService.getUserByRoleName(role);

        thesisUserService.getStudentInThesisUsers().forEach(thesisUser -> {
            users.remove(thesisUser.getUserId());
        });

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

	@PostMapping(path = "/init-account/", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
					MediaType.APPLICATION_JSON_VALUE
			})
    @CrossOrigin
    public ResponseEntity<CurrentUserDetailDto> changePasswordAndUploadAvatar(
            @RequestParam("password") String password,
			@RequestPart("avatar") MultipartFile files) {

        if (password == null || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.getCurrentLoginUser();

		if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            user.setActive(true);
            userService.saveUser(user);

            if (files != null && !files.isEmpty()) {
                user.setFile(files);
                userService.setCloudinaryField(user);
            }

			CurrentUserDetailDto c = new CurrentUserDetailDto(user, user.getFacultyId(),
					user.getRoleId());

			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

	@GetMapping(path = "/theses/")
	@CrossOrigin
	public ResponseEntity<List<Thesis>> getThesesCurrentUser() {
		User user = userService.getCurrentLoginUser();

		List<ThesisUser> thesisUsers = thesisUserService.getThesisByUser(user);

		List<Thesis> theses = new ArrayList<>();

		if (thesisUsers != null) {
			for (int i = 0; i < thesisUsers.size(); i++)
				theses.add(thesisUsers.get(i).getThesisId());

            return new ResponseEntity<>(theses, HttpStatus.OK);
        }

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

    @GetMapping("/committees/")
    @CrossOrigin
    public ResponseEntity<List<CommitteeDetailDTO>> getCommitteeCurrentUser() {
        User user = userService.getCurrentLoginUser();

        List<CommitteeUser> committeeUserList = committeeUserService.getCommitteeUserByUser(user);

		List<CommitteeDetailDTO> committeeList = new ArrayList<>();

        if (committeeUserList != null) {
            for (CommitteeUser c : committeeUserList) {
                CommitteeDetailDTO committee = new CommitteeDetailDTO();
                committee.setId(c.getCommitteeId().getId());
                committee.setName(c.getCommitteeId().getName());


                List<CommitteeUserDetailDTO> memberList = new ArrayList<>();

                for (CommitteeUser m: committeeUserService.getAllUsersOfCommittee(c.getCommitteeId().getId())) {
                    CommitteeUserDetailDTO member = new CommitteeUserDetailDTO();
                    member.setRole(m.getRole());
                    member.setUser(m.getUserId());

                    memberList.add(member);
                }

                committee.setMembers(memberList);
                committeeList.add(committee);
            }
        }
        return new ResponseEntity<>(committeeList, HttpStatus.OK);
    }

    @GetMapping("/lecturer/theses/")
    @CrossOrigin
    public ResponseEntity<List<Thesis>> getThesesOfLecturer() {
        User user = userService.getCurrentLoginUser();

        List<CommitteeUser> committeeUserList = committeeUserService.getCommitteeUserByUser(user);

        List<Thesis> theses = new ArrayList<>();

        if (committeeUserList != null) {
            for (CommitteeUser c : committeeUserList) {
                Committee committee = c.getCommitteeId();
                List<ThesisCommitteeRate> thesisCommitteeRateList =
                        thesisCommitteeRateService.getThesisCommitteeRatesByCommitteeId(committee.getId());

                if (thesisCommitteeRateList != null) {
                    for (ThesisCommitteeRate thesisCommitteeRate : thesisCommitteeRateList) {
                        Thesis thesis = thesisCommitteeRate.getThesisId();

                        thesis.setIsScoring(scoreService.isScoring(thesis.getId(), c.getId()));

                        if (thesis.getActive())
                            theses.add(thesis);
                    }
                }
            }
        }

        return new ResponseEntity<>(theses, HttpStatus.OK);
    }

    @PostMapping(path = "/avatar/",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @CrossOrigin
    public ResponseEntity<CurrentUserDetailDto> changeAvatar(@RequestPart("avatar") MultipartFile files) {
        User user = userService.getCurrentLoginUser();

        if (files == null || files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (user != null) {
            user.setFile(files);
            userService.setCloudinaryField(user);

            CurrentUserDetailDto currentUserDetailDto = new CurrentUserDetailDto(user, user.getFacultyId(), user.getRoleId());

            return new ResponseEntity<>(currentUserDetailDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/password/",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO password) {

        if (password == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.getCurrentLoginUser();

        if (user != null) {
           if ( passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(password.getNewPassword()));
                userService.saveUser(user);
                return new ResponseEntity<>(HttpStatus.OK);
           }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Chức năng Forget Password
    @PostMapping(value = "/forget-password/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<?> sendOtpChangePassword(@RequestBody UsernameDTO username) throws MessagingException {
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByUsername(username.getUsername());

        if (user != null) {
            otpService.generateOrUpdateOtp(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/otp/")
    @CrossOrigin
    public ResponseEntity<?> checkOtp(@RequestBody OtpDTO otpDTO) {
        if (otpDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userService.getUserByUsername(otpDTO.getUsername());

        if (user != null) {
            if (otpService.validateOtp(user, otpDTO.getOtp_code())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/replace-password/")
    @CrossOrigin
    public ResponseEntity<?> replacePassword(@RequestBody ForgetPasswordDTO password) {
        if (password == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        User user = userService.getUserByUsername(password.getUsername());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (otpService.validateOtp(user, password.getOtp_code())) {
			user.setPassword(passwordEncoder.encode(password.getPassword()));
			userService.saveUser(user);
            otpService.deleteOtp(user);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
