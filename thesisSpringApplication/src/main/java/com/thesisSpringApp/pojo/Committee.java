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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "committee")
@Data
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Committee.findAll", query = "SELECT c FROM Committee c"),
    @NamedQuery(name = "Committee.findById", query = "SELECT c FROM Committee c WHERE c.id = :id"),
    @NamedQuery(name = "Committee.findByName", query = "SELECT c FROM Committee c WHERE c.name = :name")})
public class Committee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "committeeId")
	@JsonIgnore
    private List<ThesisCommitteeRate> thesisCommitteeRateList;
    @OneToMany(mappedBy = "committeeId")
	@JsonIgnore
    private List<CommitteeUser> committeeUserList;

    public Committee() {
    }

    public Committee(Integer id) {
        this.id = id;
    }

    public Committee(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @XmlTransient
    public List<ThesisCommitteeRate> getThesisCommitteeRateList() {
        return thesisCommitteeRateList;
    }

    public void setThesisCommitteeRateList(List<ThesisCommitteeRate> thesisCommitteeRateList) {
        this.thesisCommitteeRateList = thesisCommitteeRateList;
    }

    @XmlTransient
    public List<CommitteeUser> getCommitteeUserList() {
        return committeeUserList;
    }

    public void setCommitteeUserList(List<CommitteeUser> committeeUserList) {
        this.committeeUserList = committeeUserList;
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
        if (!(object instanceof Committee)) {
            return false;
        }
        Committee other = (Committee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.Committee[ id=" + id + " ]";
    }
    
}
