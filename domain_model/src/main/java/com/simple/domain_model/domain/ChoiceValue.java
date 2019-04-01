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
//todo изменить генератор Id
public class ChoiceValue {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    ChoiceValue(Long valueId, String value) {
        this.valueId = valueId;
        this.value = value;
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

    public ChoiceValue getParent() {
        return parentId;
    }

    public ChoiceValue setParent(ChoiceValue parent) {
        this.parentId = parent;
        return this;
    }

    public ChoiceValue setChoiceSet(ChoiceSet choiceSet) {
        this.choiceSet = choiceSet;
        return this;
    }

    public ChoiceValue addValue(ChoiceValue value) {
        this.childValues.add(value.setParent(this));
        return this;
    }

    public ChoiceValue addValuesNode(ChoiceValue value){
        this.childValues.add(value.setParent(this));
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
