package com.mlorenzo.spring5reactivemongorecipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "unitOfMeasures")
public class UnitOfMeasure {

    @Id
    private String id;
    
    private String description;
}
