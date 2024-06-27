package com.thesisSpringApp.service;

import com.thesisSpringApp.pojo.Criteria;

import java.util.List;

public interface CriteriaService {
    Criteria getCriteriaById(int id);

    List<Criteria> getCriteriaList();

    void saveAndUpdateCriteria(Criteria criteria);
}
