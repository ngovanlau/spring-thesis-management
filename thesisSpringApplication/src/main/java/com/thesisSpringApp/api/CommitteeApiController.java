package com.thesisSpringApp.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thesisSpringApp.Dto.CommitteeDetailDTO;
import com.thesisSpringApp.Dto.CommitteePageDTO;
import com.thesisSpringApp.Dto.CommitteeUserDetailDTO;
import com.thesisSpringApp.Dto.CommitteeUserDto;
import com.thesisSpringApp.Dto.NewCommitteeDto;
import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisCommitteeRate;
import com.thesisSpringApp.pojo.ThesisStatus;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.CommitteeService;
import com.thesisSpringApp.service.CommitteeUserService;
import com.thesisSpringApp.service.CriteriaService;
import com.thesisSpringApp.service.MailSenderService;
import com.thesisSpringApp.service.ScoreService;
import com.thesisSpringApp.service.ThesisCommitteeRateService;
import com.thesisSpringApp.service.ThesisService;
import com.thesisSpringApp.service.ThesisStatusService;
import com.thesisSpringApp.service.ThesisUserService;
import com.thesisSpringApp.service.UserService;

@RestController
@RequestMapping("/api/committees")
@CrossOrigin
public class CommitteeApiController {

	private CommitteeService committeeService;
	private UserService userService;
	private CommitteeUserService committeeUserService;
	private MailSenderService mailSenderService;
	private Environment env;
	private ThesisService thesisService;
	private ThesisCommitteeRateService thesisCommitteeRateService;
	private ScoreService scoreService;
	private ThesisStatusService thesisStatusService;
	private CriteriaService criteriaService;
	private ThesisUserService thesisUserService;

	@Autowired
	public CommitteeApiController(CommitteeService committeeService, UserService userService,
		CommitteeUserService committeeUserService, MailSenderService mailSenderService,
  		Environment env, ThesisService thesisService, ThesisCommitteeRateService thesisCommitteeRateService,
  		ScoreService scoreService, ThesisStatusService thesisStatusService,
			CriteriaService criteriaService, ThesisUserService thesisUserService) {
		super();
		this.committeeService = committeeService;
		this.userService = userService;
		this.committeeUserService = committeeUserService;
		this.mailSenderService = mailSenderService;
		this.env = env;
		this.thesisService = thesisService;
		this.thesisCommitteeRateService = thesisCommitteeRateService;
		this.scoreService = scoreService;
		this.thesisStatusService = thesisStatusService;
		this.criteriaService = criteriaService;
		this.thesisUserService = thesisUserService;
	}

	@PostMapping(path = "/", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
	})
	@CrossOrigin
	public ResponseEntity<Committee> addNewCommittee(@RequestBody NewCommitteeDto newCommitteeDto)
			throws MessagingException {

		if (newCommitteeDto.getCommitteeUserDtos().isEmpty() || newCommitteeDto.getCommitteeUserDtos().size() > 5) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Committee committee = new Committee();
		String committeeName = newCommitteeDto.getName();
		committee.setName(committeeName);
		committee.setActive(true);
		committeeService.saveCommittee(committee);

		List<CommitteeUserDto> committeeUserDtos = newCommitteeDto.getCommitteeUserDtos();

		for (CommitteeUserDto c : committeeUserDtos) {
			CommitteeUser cmU = new CommitteeUser();
			cmU.setRole(c.getRoleName());

			User user = userService.getUserById(c.getUserId());

			if (c.getRoleName().equals("Phản biện"))
				mailSenderService.sendEmailForLecture(env.getProperty("spring.mail.username"),
						user, committee);
				System.out.println("Đã gửi mail");

			cmU.setUserId(user);
			cmU.setCommitteeId(committee);

			committeeUserService.saveCommitteeUser(cmU);

		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public List<CommitteeDetailDTO> responseCommitteeDetail(Map<String,String> params) {
		List<Committee> committees = this.committeeService.getAllCommittee(params);

		List<CommitteeDetailDTO> committeeList = new ArrayList<>();

		for (Committee c : committees) {
			CommitteeDetailDTO committee = new CommitteeDetailDTO();
			committee.setId(c.getId());
			committee.setName(c.getName());
			committee.setActive(c.getActive());

			List<CommitteeUserDetailDTO> memberList = new ArrayList<>();

			for (CommitteeUser m: committeeUserService.getAllUsersOfCommittee(c.getId())) {
				CommitteeUserDetailDTO member = new CommitteeUserDetailDTO();
				member.setRole(m.getRole());
				member.setUser(m.getUserId());

				memberList.add(member);
			}
			committee.setMembers(memberList);
			committeeList.add(committee);
		}

		return committeeList;
	}

	@DeleteMapping(path = "/{committeeId}/")
	@CrossOrigin
	public ResponseEntity<Committee> deleteCommittee(
			@PathVariable(value = "committeeId") int committeeId) {

		committeeService.deleteCommitteeById(committeeId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/")
	@CrossOrigin
	public ResponseEntity<CommitteePageDTO> list(@RequestParam Map<String, String> params) {
		CommitteePageDTO committeePageDTO = new CommitteePageDTO();
		committeePageDTO.setTotalPages(committeeService.totalCommitteePages());

		String page = params.get("page");
		if (page == null || page.isEmpty()) {
			params.put("page", "1");
		}

		List<CommitteeDetailDTO> committeeList = responseCommitteeDetail(params);
		committeePageDTO.setResult(committeeList);

		return new ResponseEntity<>(committeePageDTO, HttpStatus.OK);
	}

	@GetMapping("/active/")
	public ResponseEntity<List<Committee>> listCommitteeForThesis() {
		List<Committee> committees = committeeService.getCommitteesForThesis();

		return new ResponseEntity<>(committees, HttpStatus.OK);
	}

	@PatchMapping("/{committeeId}/")
	@CrossOrigin
	public ResponseEntity<List<CommitteeDetailDTO>> closeCommittee(
			@PathVariable(value = "committeeId") int committeeId) throws MessagingException {

		if (committeeId < 1) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Committee committee = committeeService.getCommitteeById(committeeId);
		committee.setActive(!committee.getActive());

		if (!committee.getActive()) {
			List<ThesisCommitteeRate> thesisCommitteeRateList = thesisCommitteeRateService
					.getThesisCommitteeRatesByCommitteeId(committee.getId());
			for (ThesisCommitteeRate t : thesisCommitteeRateList) {
				Thesis thesis = t.getThesisId();

				// Xem khóa luận đã được chấm chưa
				if (!t.getStatusId().getId().equals(3)) {
					List<Score> scores = scoreService.getScoresByThesisId(thesis.getId());
					int committeeUserCount = committeeUserService
							.getAllUsersOfCommittee(committee.getId()).size();
					int criteriaCount = criteriaService.getCriteriaList().size();

					// Tính điểm
					float score = 0;
					for (Score s : scores) {
						score += s.getScore();
					}
					score = (float) Math.ceil((score / (committeeUserCount * criteriaCount)) * 100) / 100;

					// Lưu các thay đổi
					thesis.setScore(score);
					thesis.setActive(false);
					thesisService.saveAndUpdateThesis(thesis);

					ThesisStatus thesisStatus = thesisStatusService.getThesisStatusById(3);
					t.setStatusId(thesisStatus);
					thesisCommitteeRateService.saveAndUpdateThesisCommitteeRate(t);

				}
				// Thông báo qua email cho sinh viên
				List<ThesisUser> thesisUsers = thesisUserService.getUserByThesis(thesis);
				for (ThesisUser tus : thesisUsers)
					if (tus.getUserId().getRoleId().getName().equals("ROLE_SINHVIEN"))
						mailSenderService.sendEmailForPupils(
								env.getProperty("spring.mail.username"),
								tus.getUserId(), thesis);
			}
		}
		committeeService.saveCommittee(committee);

		Map<String, String> params = new HashMap<>();
		List<CommitteeDetailDTO> committeeList = responseCommitteeDetail(params);

		return new ResponseEntity<>(committeeList, HttpStatus.OK);
	}
}