package com.example.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

public class CopyNonNullProperties {
    // Подавление создания конструктора по умолчанию
    // для достижения неинстанцируемости
    private CopyNonNullProperties() {
        throw new AssertionError("Не должно быть возможно создать экземпляр этого класса");
    }

    public static void copy(Object src, Object target) {
        BeanWrapper srcWrapper = new BeanWrapperImpl(src);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        PropertyDescriptor[] pds = srcWrapper.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null && srcWrapper.getPropertyValue(pd.getName()) != null) {
                targetWrapper.setPropertyValue(pd.getName(), srcWrapper.getPropertyValue(pd.getName()));
            }
        }
    }
}
