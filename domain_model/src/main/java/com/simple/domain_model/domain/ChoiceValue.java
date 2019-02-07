package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name = "choice_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChoiceValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueId;

    @ManyToOne
    private ChoiceValue parent_id;

    @NotNull
    private String value;

    @ManyToOne
    private ChoiceSet choice_set;

    @OneToMany (mappedBy = "parent_id", orphanRemoval = true, cascade = CascadeType.ALL)
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
        return choice_set;
    }

    public ChoiceValue getParentId() {
        return parent_id;
    }

    public ChoiceValue setParentId(ChoiceValue parentId) {
        this.parent_id = parentId;
        return this;
    }

    public ChoiceValue setChoiceSet(ChoiceSet choiceSet) {
        this.choice_set = choiceSet;
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
