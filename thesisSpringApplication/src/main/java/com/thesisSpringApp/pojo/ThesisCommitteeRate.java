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

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "thesis_committee_rate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThesisCommitteeRate.findAll", query = "SELECT t FROM ThesisCommitteeRate t"),
    @NamedQuery(name = "ThesisCommitteeRate.findById", query = "SELECT t FROM ThesisCommitteeRate t WHERE t.id = :id")})
public class ThesisCommitteeRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "committee_id", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
    private Committee committeeId;
    @JoinColumn(name = "thesis_id", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
    private Thesis thesisId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
    private ThesisStatus statusId;

    public ThesisCommitteeRate() {
    }

    public ThesisCommitteeRate(Integer id) {
        this.id = id;
    }


	public ThesisCommitteeRate(Committee committeeId, Thesis thesisId, ThesisStatus statusId) {
		super();
		this.committeeId = committeeId;
		this.thesisId = thesisId;
		this.statusId = statusId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Committee getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Committee committeeId) {
        this.committeeId = committeeId;
    }

    public Thesis getThesisId() {
        return thesisId;
    }

    public void setThesisId(Thesis thesisId) {
        this.thesisId = thesisId;
    }

    public ThesisStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(ThesisStatus statusId) {
        this.statusId = statusId;
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
        if (!(object instanceof ThesisCommitteeRate)) {
            return false;
        }
        ThesisCommitteeRate other = (ThesisCommitteeRate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.ThesisCommitteeRate[ id=" + id + " ]";
    }
    
}
