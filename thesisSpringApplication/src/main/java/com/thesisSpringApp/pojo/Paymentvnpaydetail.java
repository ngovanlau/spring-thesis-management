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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "paymentvnpaydetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paymentvnpaydetail.findAll", query = "SELECT p FROM Paymentvnpaydetail p"),
    @NamedQuery(name = "Paymentvnpaydetail.findById", query = "SELECT p FROM Paymentvnpaydetail p WHERE p.id = :id"),
    @NamedQuery(name = "Paymentvnpaydetail.findByOrderId", query = "SELECT p FROM Paymentvnpaydetail p WHERE p.orderId = :orderId"),
    @NamedQuery(name = "Paymentvnpaydetail.findByAmount", query = "SELECT p FROM Paymentvnpaydetail p WHERE p.amount = :amount"),
    @NamedQuery(name = "Paymentvnpaydetail.findByOrderDesc", query = "SELECT p FROM Paymentvnpaydetail p WHERE p.orderDesc = :orderDesc"),
    @NamedQuery(name = "Paymentvnpaydetail.findByVnpTransactionNo", query = "SELECT p FROM Paymentvnpaydetail p WHERE p.vnpTransactionNo = :vnpTransactionNo"),
    @NamedQuery(name = "Paymentvnpaydetail.findByVnpResponseCode", query = "SELECT p FROM Paymentvnpaydetail p WHERE p.vnpResponseCode = :vnpResponseCode")})
public class Paymentvnpaydetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "order_id")
    private String orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private float amount;
    @Size(max = 255)
    @Column(name = "order_desc")
    private String orderDesc;
    @Size(max = 255)
    @Column(name = "vnp_TransactionNo")
    private String vnpTransactionNo;
    @Size(max = 255)
    @Column(name = "vnp_ResponseCode")
    private String vnpResponseCode;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Paymentvnpaydetail() {
    }

    public Paymentvnpaydetail(Integer id) {
        this.id = id;
    }

    public Paymentvnpaydetail(Integer id, String orderId, float amount) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getVnpTransactionNo() {
        return vnpTransactionNo;
    }

    public void setVnpTransactionNo(String vnpTransactionNo) {
        this.vnpTransactionNo = vnpTransactionNo;
    }

    public String getVnpResponseCode() {
        return vnpResponseCode;
    }

    public void setVnpResponseCode(String vnpResponseCode) {
        this.vnpResponseCode = vnpResponseCode;
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
        if (!(object instanceof Paymentvnpaydetail)) {
            return false;
        }
        Paymentvnpaydetail other = (Paymentvnpaydetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.Paymentvnpaydetail[ id=" + id + " ]";
    }
    
}
