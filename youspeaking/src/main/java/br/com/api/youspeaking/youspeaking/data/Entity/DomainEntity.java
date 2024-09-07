package br.com.api.youspeaking.youspeaking.data.Entity;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_DOMAIN")
@Getter
@Setter
@NoArgsConstructor
public class DomainEntity {
    
    @Id 
    @Column private Long domainCode;
    @Column private String domainValue;
    @Column private Long domainOrder;
    @Column private Long domainCodeDad;
    @Column private Long relatedValue;
    @Column private String description;
    @Column private String registerUser;
    @Column private Calendar registerDate;

}
