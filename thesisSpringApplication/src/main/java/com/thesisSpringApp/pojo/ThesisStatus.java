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

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "thesis_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThesisStatus.findAll", query = "SELECT t FROM ThesisStatus t"),
    @NamedQuery(name = "ThesisStatus.findById", query = "SELECT t FROM ThesisStatus t WHERE t.id = :id"),
    @NamedQuery(name = "ThesisStatus.findByStatusName", query = "SELECT t FROM ThesisStatus t WHERE t.statusName = :statusName")})
public class ThesisStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status_name")
    private String statusName;
    @OneToMany(mappedBy = "statusId")
    private List<ThesisCommitteeRate> thesisCommitteeRateList;

    public ThesisStatus() {
    }

    public ThesisStatus(Integer id) {
        this.id = id;
    }

    public ThesisStatus(Integer id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
        if (!(object instanceof ThesisStatus)) {
            return false;
        }
        ThesisStatus other = (ThesisStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.ThesisStatus[ id=" + id + " ]";
    }
    
}
