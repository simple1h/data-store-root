package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name = "Choice_sets", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChoiceSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;

    @Embedded
    @NotNull
    private EntityInfo info;

    @OneToMany (mappedBy = "choice_set", orphanRemoval = true, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ChoiceValue> valueList = new LinkedHashSet<>();

    ChoiceSet(EntityInfo info) {
        this.info = info;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public EntityInfo getInfo() {
        return info;
    }

    public ChoiceSet addValue(ChoiceValue value) {
        this.valueList.add(value);
        value.setChoiceSet(this);
        return this;
    }

    public ChoiceValue addValueAsParent(ChoiceValue value){
        this.valueList.add(value);
        value.setChoiceSet(this);
        return value;
    }

    public ChoiceSet removeValue(ChoiceValue value){
        this.valueList.remove(value.setChoiceSet(null));
        return this;
    }

    public Set<ChoiceValue> getValueList() {
        return Collections.unmodifiableSet(valueList);
    }
}
