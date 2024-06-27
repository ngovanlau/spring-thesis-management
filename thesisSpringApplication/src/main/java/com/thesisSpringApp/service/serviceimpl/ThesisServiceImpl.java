package com.thesisSpringApp.service.serviceimpl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.repository.ThesisRepository;
import com.thesisSpringApp.service.ThesisService;

@Service
@PropertySource("classpath:config.properties")
public class ThesisServiceImpl implements ThesisService {

	@Autowired
	private ThesisRepository thesisRepository;
	@Autowired
	private Environment env;

	@Override
	public void saveAndUpdateThesis(Thesis thesis) {

        Date date = new Date();
        if (thesis.getId() != null && thesis.getId() > 0) {
            thesis.setUpdateDate(date);
		} else {
            thesis.setCreateDate(date);
			thesis.setUpdateDate(date);
			thesis.setActive(true);
		}

		thesisRepository.saveAndUpdateThesis(thesis);
	}

	@Override
	public Thesis getThesisById(int id) {
		return this.thesisRepository.getThesisById(id);
	}

	@Override
	public List<Thesis> getAllThesis(Map<String, String> params) {
		return this.thesisRepository.getAllThesis(params);
	}

	@Override
	public int totalPages() {
		Map<String, String> params = new HashMap<>();

		return (int) Math.ceil((double) thesisRepository.getAllThesis(params).size() / Integer.parseInt(env.getProperty("theses.pageSize").toString()));
	}

	@Override
	public void deleteThesisById(int id) {
		thesisRepository.deleteThesisById(id);
	}

}
