/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.pojo;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "committee_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommitteeUser.findAll", query = "SELECT c FROM CommitteeUser c"),
    @NamedQuery(name = "CommitteeUser.findById", query = "SELECT c FROM CommitteeUser c WHERE c.id = :id"),
    @NamedQuery(name = "CommitteeUser.findByRole", query = "SELECT c FROM CommitteeUser c WHERE c.role = :role")})
public class CommitteeUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "committeeUserId")
	@JsonIgnore
    private List<Score> scoreList;
    @JoinColumn(name = "committee_id", referencedColumnName = "id")
    @ManyToOne
	@JsonIgnore
    private Committee committeeId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
	@JsonIgnore
    private User userId;

    public CommitteeUser() {
    }

    public CommitteeUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public Committee getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Committee committeeId) {
        this.committeeId = committeeId;
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
        if (!(object instanceof CommitteeUser)) {
            return false;
        }
        CommitteeUser other = (CommitteeUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.CommitteeUser[ id=" + id + " ]";
    }
    
}
