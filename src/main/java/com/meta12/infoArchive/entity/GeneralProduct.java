package com.meta12.infoArchive.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("G")
public class GeneralProduct extends Product {
    
    //강사명
    private String instructorName;
    
    //강의 소개
    private String contentSummary;
}
