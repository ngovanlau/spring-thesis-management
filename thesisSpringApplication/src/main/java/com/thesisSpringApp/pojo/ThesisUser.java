/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.pojo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "thesis_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThesisUser.findAll", query = "SELECT t FROM ThesisUser t"),
    @NamedQuery(name = "ThesisUser.findById", query = "SELECT t FROM ThesisUser t WHERE t.id = :id")})
public class ThesisUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnore
    private Thesis thesisId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.DETACH ,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnore
    private User userId;

    public ThesisUser() {
    }

    public ThesisUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Thesis getThesisId() {
        return thesisId;
    }

    public void setThesisId(Thesis thesisId) {
        this.thesisId = thesisId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof ThesisUser)) {
            return false;
        }
        ThesisUser other = (ThesisUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.ThesisUser[ id=" + id + " ]";
    }
    
}
