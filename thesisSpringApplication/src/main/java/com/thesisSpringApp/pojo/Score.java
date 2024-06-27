/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.pojo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "score")
@Data
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Score.findAll", query = "SELECT s FROM Score s"),
    @NamedQuery(name = "Score.findById", query = "SELECT s FROM Score s WHERE s.id = :id"),
    @NamedQuery(name = "Score.findByScore", query = "SELECT s FROM Score s WHERE s.score = :score")})
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private float score;
    @JoinColumn(name = "committee_user_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private CommitteeUser committeeUserId;
    @JoinColumn(name = "criteria_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Criteria criteriaId;
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Thesis thesisId;

    public Score() {
    }

    public Score(Integer id) {
        this.id = id;
    }

    public Score(Integer id, float score) {
        this.id = id;
        this.score = score;
    }

    public Score(Thesis thesis, CommitteeUser committeeUser, Criteria criteria, float score) {
        this.thesisId = thesis;
        this.committeeUserId = committeeUser;
        this.criteriaId = criteria;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public CommitteeUser getCommitteeUserId() {
        return committeeUserId;
    }

    public void setCommitteeUserId(CommitteeUser committeeUserId) {
        this.committeeUserId = committeeUserId;
    }

    public Criteria getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Criteria criteriaId) {
        this.criteriaId = criteriaId;
    }

    public Thesis getThesisId() {
        return thesisId;
    }

    public void setThesisId(Thesis thesisId) {
        this.thesisId = thesisId;
    }

//    public User getUserId() {
//        return userId;
//    }
//
//    public void setUserId(User userId) {
//        this.userId = userId;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Score)) {
            return false;
        }
        Score other = (Score) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.Score[ id=" + id + " ]";
    }
    
}
