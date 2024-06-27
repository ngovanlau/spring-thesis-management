package com.thesisSpringApp.service.serviceimpl;

import com.thesisSpringApp.pojo.Criteria;
import com.thesisSpringApp.repository.CriteriaRepository;
import com.thesisSpringApp.service.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaServiceImpl implements CriteriaService {
    @Autowired
    private CriteriaRepository criteriaRepository;

    @Override
    public Criteria getCriteriaById(int id) {
        return criteriaRepository.getCriteriaById(id);
    }

    @Override
    public List<Criteria> getCriteriaList() {
        return criteriaRepository.getCriteriaList();
    }

    @Override
    public void saveAndUpdateCriteria(Criteria criteria) {
        criteriaRepository.saveAndUpdateCriteria(criteria);
    }
}
