package com.frezrik.common.compiler;

import com.squareup.javapoet.ClassName;

import java.util.Objects;

public class BindingSet {

    ClassName target;
    String field;
    ClassName fieldType;
    ClassName fieldTypeImpl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BindingSet that = (BindingSet) o;
        return Objects.equals(target, that.target) &&
                Objects.equals(field, that.field) &&
                Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(fieldTypeImpl, that.fieldTypeImpl);
    }

}
