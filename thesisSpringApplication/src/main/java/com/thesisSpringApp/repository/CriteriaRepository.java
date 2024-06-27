package com.thesisSpringApp.repository;

import com.thesisSpringApp.pojo.Criteria;

import java.util.List;

public interface CriteriaRepository {
    Criteria getCriteriaById(int id);

    List<Criteria> getCriteriaList();

    void saveAndUpdateCriteria(Criteria criteria);
}
