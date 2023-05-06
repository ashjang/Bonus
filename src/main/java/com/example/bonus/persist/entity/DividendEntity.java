package com.example.bonus.persist.entity;

import com.example.bonus.model.Dividend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@NoArgsConstructor
@ToString
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long company_id;

    private LocalDateTime date;

    private String dividend;

    public DividendEntity(Long company_id, Dividend dividend) {
        this.company_id = company_id;
        this.date = dividend.getDate();
        this.dividend = dividend.getDividend();
    }
}
