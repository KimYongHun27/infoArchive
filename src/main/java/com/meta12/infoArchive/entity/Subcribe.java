package com.meta12.infoArchive.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("SUBSCRIBE")
public class Subcribe extends Product {
    private String instructorName;
}