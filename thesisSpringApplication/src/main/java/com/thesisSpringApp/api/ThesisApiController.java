package com.thesisSpringApp.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thesisSpringApp.Dto.CriteriaDTO;
import com.thesisSpringApp.Dto.ScoreDTO;
import com.thesisSpringApp.Dto.ScoreDetailDTO;
import com.thesisSpringApp.Dto.ThesesPageDTO;
import com.thesisSpringApp.Dto.ThesisCommitteeDTO;
import com.thesisSpringApp.Dto.ThesisDTO;
import com.thesisSpringApp.Dto.ThesisDetailDTO;
import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.pojo.Criteria;
import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisCommitteeRate;
import com.thesisSpringApp.pojo.ThesisStatus;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.CommitteeService;
import com.thesisSpringApp.service.CommitteeUserService;
import com.thesisSpringApp.service.CriteriaService;
import com.thesisSpringApp.service.RoleService;
import com.thesisSpringApp.service.ScoreService;
import com.thesisSpringApp.service.ThesisCommitteeRateService;
import com.thesisSpringApp.service.ThesisService;
import com.thesisSpringApp.service.ThesisStatusService;
import com.thesisSpringApp.service.ThesisUserService;
import com.thesisSpringApp.service.UserService;

@RestController
@RequestMapping("/api/theses")
public class ThesisApiController {

	private ThesisService thesisService;
	private ThesisUserService thesisUserService;
	private UserService userService;
	private CommitteeService committeeService;
	private ThesisStatusService thesisStatusService;
	private CommitteeUserService committeeUserService;
	private ThesisCommitteeRateService thesisCommitteeRateService;
	private RoleService roleService;
	private CriteriaService criteriaService;
	private ScoreService scoreService;

	@Autowired
	public ThesisApiController(ThesisService thesisService, ThesisUserService thesisUserService,
			UserService userService, CommitteeService committeeService,
			CommitteeUserService committeeUserService,
			ThesisStatusService thesisStatusService, ThesisCommitteeRateService thesisCommitteeRateService,
		    RoleService roleService, CriteriaService criteriaService, ScoreService scoreService) {
		super();
		this.thesisService = thesisService;
		this.thesisUserService = thesisUserService;
		this.userService = userService;
		this.committeeService = committeeService;
		this.thesisStatusService = thesisStatusService;
		this.committeeUserService = committeeUserService;
		this.thesisCommitteeRateService = thesisCommitteeRateService;
		this.roleService = roleService;
		this.criteriaService = criteriaService;
		this.scoreService = scoreService;
	}

	@PostMapping(path = "/")
	@CrossOrigin
	public ResponseEntity<Thesis> addNewThesis(@RequestBody ThesisDTO thesisDTO) {
		Thesis thesis = new Thesis();
		String name = thesisDTO.getName();
		thesis.setName(name);
		thesisService.saveAndUpdateThesis(thesis);

		List<Integer> userIds = thesisDTO.getUserIds();

		for (int i = 0; i < userIds.size(); i++)
			thesisUserService.addNewThesisUser(new ThesisUser(), thesis,
					userService.getUserById(userIds.get(i)));

		return new ResponseEntity<>(thesis, HttpStatus.CREATED);
	}

	@GetMapping("/")
	@CrossOrigin
	public ResponseEntity<ThesesPageDTO> list(@RequestParam Map<String, String> params) {
		ThesesPageDTO thesesPageDTO = new ThesesPageDTO();
		thesesPageDTO.setTotalPages(thesisService.totalPages());

		String page = params.get("page");
		if (page == null || page.isEmpty()) {
			params.put("page", "1");
		}

		List<Thesis> theses = this.thesisService.getAllThesis(params);
		thesesPageDTO.setResult(theses);

		return new ResponseEntity<>(thesesPageDTO, HttpStatus.OK);
	}

	public ThesisDetailDTO responseThesisDetail(Thesis thesis) {
		ThesisDetailDTO thesisDetailDTO = new ThesisDetailDTO();
		thesisDetailDTO.setThesis(thesis);

		List<ThesisUser> thesisUserList = thesisUserService.getUserByThesis(thesis);

		List<User> lecturers = new ArrayList<>();
		List<User> students = new ArrayList<>();

		for (ThesisUser thesisUser : thesisUserList) {
			if (thesisUser.getUserId().getRoleId().getName().equals("ROLE_GIANGVIEN"))
				lecturers.add(thesisUser.getUserId());
			else
				students.add(thesisUser.getUserId());
		}
		thesisDetailDTO.setLecturers(lecturers);
		thesisDetailDTO.setStudents(students);

		Committee committee = committeeService.getCommitteeOfThesis(thesis.getId());
		thesisDetailDTO.setCommittee(committee);

		List<Score> scoreList = scoreService.getScoresByThesisId(thesis.getId());
		List<ScoreDetailDTO> scores = new ArrayList<>();
		if (scoreList != null) {
			for (Score s : scoreList) {
				ScoreDetailDTO scoreDetailDTO = new ScoreDetailDTO();
				scoreDetailDTO.setId(s.getId());
				scoreDetailDTO.setCriteriaId(s.getCriteriaId().getId());
				scoreDetailDTO.setUserId(s.getCommitteeUserId().getUserId().getId());
				scoreDetailDTO.setScore(s.getScore());

				scores.add(scoreDetailDTO);
			}
		}
		thesisDetailDTO.setScores(scores);

		return thesisDetailDTO;
	}

	@GetMapping(path = "/{thesisId}/", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ThesisDetailDTO> retrieve(@PathVariable(value = "thesisId") int thesisId) {
		Thesis thesis = thesisService.getThesisById(thesisId);

		ThesisDetailDTO thesisDetailDTO = responseThesisDetail(thesis);

		return new ResponseEntity<>(thesisDetailDTO, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{thesisId}/")
	@CrossOrigin
	public ResponseEntity<Thesis> deleteThesisId(
			@PathVariable(value = "thesisId") int thesisId) {
		Thesis thesis = thesisService.getThesisById(thesisId);
		thesisService.deleteThesisById(thesisId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping(path = "/committee/", consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin
	public ResponseEntity<ThesisDetailDTO> addCommittee(
			@RequestBody ThesisCommitteeDTO thesisCommitteeDTO) {

		Thesis thesis = thesisService.getThesisById(thesisCommitteeDTO.getThesisId());
		Committee committee = committeeService
				.getCommitteeById(thesisCommitteeDTO.getCommitteeId());

		if (!committee.getActive()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		ThesisStatus thesisStatus = thesisStatusService.getThesisStatusById(2);

		ThesisCommitteeRate thesisCommitteeRate = thesisCommitteeRateService
				.getThesisCommitteeRateByThesisId(thesis.getId());

		if (thesisCommitteeRate == null) {
			thesisCommitteeRate = new ThesisCommitteeRate(committee, thesis, thesisStatus);
			thesis.setActive(true);
			thesisService.saveAndUpdateThesis(thesis);
		}
		else
			thesisCommitteeRate.setCommitteeId(committee);

		thesisCommitteeRateService.saveAndUpdateThesisCommitteeRate(thesisCommitteeRate);

		ThesisDetailDTO thesisDetailDTO = responseThesisDetail(thesis);

		return new ResponseEntity<>(thesisDetailDTO, HttpStatus.OK);
	}

	@PostMapping(path = "/scores/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	public void addScore(@RequestBody ScoreDTO scoreDTO) {
		Thesis thesis = thesisService.getThesisById(scoreDTO.getThesisId());
		User user = userService.getCurrentLoginUser();
		CommitteeUser committeeUser = committeeUserService.getCommitteeUser(user.getId(),
				scoreDTO.getCommitteeId());
		List<CriteriaDTO> criteriaDTOList = scoreDTO.getScores();

		for (CriteriaDTO criteriaDTO : criteriaDTOList) {
			Criteria criteria = criteriaService.getCriteriaById(criteriaDTO.getCriteriaId());
			Score score = scoreService.getScore(thesis.getId(), committeeUser.getId(), criteria.getId());

			if (score == null)
				score = new Score(thesis, committeeUser, criteria, criteriaDTO.getScore());
			else
				score.setScore(criteriaDTO.getScore());

			scoreService.saveAndUpdateScore(score);
		}
	}

}