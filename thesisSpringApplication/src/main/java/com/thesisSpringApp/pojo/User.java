/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.pojo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thesisSpringApp.customAnnotation.UniqueValueField;
import com.thesisSpringApp.customAnnotation.validationGroups.UniqueFieldsGroups.CreateGroup;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByAvatar", query = "SELECT u FROM User u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "User.findByUseruniversityid", query = "SELECT u FROM User u WHERE u.useruniversityid = :useruniversityid"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
    @NamedQuery(name = "User.findByBirthday", query = "SELECT u FROM User u WHERE u.birthday = :birthday"),
    @NamedQuery(name = "User.findByActive", query = "SELECT u FROM User u WHERE u.active = :active")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Basic(optional = false)
// @NotNull
	@UniqueValueField(fieldName = "useruniversityid", message = "{user.uid.exists}", groups = CreateGroup.class)
	@Size(min = 1, max = 10, message = "{user.useruniversityid.minMaxLenErr}")
    @Column(name = "useruniversityid")
    private String useruniversityid;

    @Basic(optional = false)
// @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
// @NotNull
    @Size(min = 1, max = 255)
	@JsonIgnore
    @Column(name = "password")
    private String password;

    @Size(max = 40)
    @Column(name = "firstName")
    private String firstName;

    @Size(max = 40)
    @Column(name = "lastName")
    private String lastName;

    @Size(max = 10)
    @Column(name = "gender")
    private String gender;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
// @NotNull
	@UniqueValueField(fieldName = "email", message = "{user.email.exists}", groups = CreateGroup.class)
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;

    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
	@Size(max = 10, message = "{user.phone.minMaxLenErr}")
    @Column(name = "phone")
	@UniqueValueField(fieldName = "phone", message = "{user.phone.exists}", groups = CreateGroup.class)
    private String phone;

    @Basic(optional = false)
// @NotNull
    @Column(name = "birthday")
//  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "{user.birthday.past}")
    private Date birthday;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "userId")
	@JsonIgnore
    private List<ThesisUser> thesisUserList;

    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	@JsonIgnore
    private Faculty facultyId;

    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	@JsonIgnore
    private Role roleId;

    @OneToMany(mappedBy = "userId")
	@JsonIgnore
    private List<CommitteeUser> committeeUserList;

	@JsonIgnore
	@OneToMany(mappedBy = "userId")
	private List<Paymentvnpaydetail> paymentvnpaydetailList;

	@OneToOne(mappedBy = "userId")
	@JsonIgnore
	private Otp otpId;

	@Transient
	@JsonIgnore
	private MultipartFile file;


    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getUseruniversityid() {
		return useruniversityid;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public Boolean getActive() {
		return active;
	}

	@XmlTransient
	public List<ThesisUser> getThesisUserList() {
		return thesisUserList;
	}

	public Faculty getFacultyId() {
		return facultyId;
	}

	public Role getRoleId() {
		return roleId;
	}

	@XmlTransient
	public List<CommitteeUser> getCommitteeUserList() {
		return committeeUserList;
	}

	@XmlTransient
	public MultipartFile getFile() {
		return file;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setUseruniversityid(String useruniversityid) {
		this.useruniversityid = useruniversityid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setThesisUserList(List<ThesisUser> thesisUserList) {
		this.thesisUserList = thesisUserList;
	}

	public void setFacultyId(Faculty facultyId) {
		this.facultyId = facultyId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public void setCommitteeUserList(List<CommitteeUser> committeeUserList) {
		this.committeeUserList = committeeUserList;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thesisSpringApp.pojo.User[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Paymentvnpaydetail> getPaymentvnpaydetailList() {
        return paymentvnpaydetailList;
    }

    public void setPaymentvnpaydetailList(List<Paymentvnpaydetail> paymentvnpaydetailList) {
        this.paymentvnpaydetailList = paymentvnpaydetailList;
    }

}
