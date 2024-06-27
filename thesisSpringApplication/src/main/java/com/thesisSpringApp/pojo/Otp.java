package com.thesisSpringApp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@Entity
@Data
@XmlRootElement
@Table(name = "otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne
	@JsonIgnore
    private User userId;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    public Otp() {
    }

    public Otp(User user) {
        this.userId = user;
    }
}
