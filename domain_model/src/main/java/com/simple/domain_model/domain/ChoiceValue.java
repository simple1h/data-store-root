package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "choice_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//todo добавить методы equals/hashCode/toString
public class ChoiceValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueId;

    @ManyToOne
    private ChoiceValue parentId;

    private String value;

    @ManyToOne
    private ChoiceSet choiceSet;

    @OneToMany (mappedBy = "parentId", orphanRemoval = true, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ChoiceValue> childValues = new LinkedHashSet<>();

    ChoiceValue(String value) {
        setValue(value);
    }

    public Long getValueId() {
        return valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ChoiceSet getChoiceSet() {
        return choiceSet;
    }

    public ChoiceValue getParentId() {
        return parentId;
    }

    public ChoiceValue setParentId(ChoiceValue parentId) {
        this.parentId = parentId;
        return this;
    }

    public ChoiceValue setChoiceSet(ChoiceSet choiceSet) {
        this.choiceSet = choiceSet;
        return this;
    }

    public ChoiceValue addValue(ChoiceValue value) {
        this.childValues.add(value.setParentId(this));
        return this;
    }

    public ChoiceValue addValueAsParent(ChoiceValue value){
        this.childValues.add(value.setParentId(this));
        return value;
    }

    public ChoiceValue removeValue(ChoiceValue value){
        this.childValues.remove(value.setChoiceSet(null));
        return this;
    }

    public Set<ChoiceValue> getChildValues() {
        return Collections.unmodifiableSet(childValues);
    }

}
