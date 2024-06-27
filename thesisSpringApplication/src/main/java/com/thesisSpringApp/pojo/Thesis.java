/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 *
 * @author ADMIN
 */
@Data
@Entity
@Table(name = "thesis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Thesis.findAll", query = "SELECT t FROM Thesis t"),
    @NamedQuery(name = "Thesis.findById", query = "SELECT t FROM Thesis t WHERE t.id = :id"),
    @NamedQuery(name = "Thesis.findByName", query = "SELECT t FROM Thesis t WHERE t.name = :name"),
    @NamedQuery(name = "Thesis.findByCreateDate", query = "SELECT t FROM Thesis t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "Thesis.findByUpdateDate", query = "SELECT t FROM Thesis t WHERE t.updateDate = :updateDate"),
    @NamedQuery(name = "Thesis.findByScore", query = "SELECT t FROM Thesis t WHERE t.score = :score"),
    @NamedQuery(name = "Thesis.findByActive", query = "SELECT t FROM Thesis t WHERE t.active = :active")})
public class Thesis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "score")
    private Float score;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "thesisId")
    @JsonIgnore
    private List<Score> scoreList;
    @OneToMany(mappedBy = "thesisId")
    @JsonIgnore
    private List<ThesisUser> thesisUserList;
    @OneToMany(mappedBy = "thesisId")
    @JsonIgnore
    private List<ThesisCommitteeRate> thesisCommitteeRateList;
    @Transient
    private Boolean isScoring;

    public Thesis() {
    }

    public Thesis(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @XmlTransient
    public List<ThesisUser> getThesisUserList() {
        return thesisUserList;
    }

    public void setThesisUserList(List<ThesisUser> thesisUserList) {
        this.thesisUserList = thesisUserList;
    }

    @XmlTransient
    public List<ThesisCommitteeRate> getThesisCommitteeRateList() {
        return thesisCommitteeRateList;
    }

    public void setThesisCommitteeRateList(List<ThesisCommitteeRate> thesisCommitteeRateList) {
        this.thesisCommitteeRateList = thesisCommitteeRateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Thesis)) {
            return false;
        }
        Thesis other = (Thesis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.Thesis[ id=" + id + " ]";
    }
    
}
