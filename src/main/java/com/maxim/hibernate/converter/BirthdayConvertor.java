package com.maxim.hibernate.converter;

import java.sql.Date;
import java.util.Optional;

import com.maxim.hibernate.entity.Birthday;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BirthdayConvertor implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                        .map(date -> attribute.birthDate())
                        .map(Date::valueOf)
                        .orElse(null);
                        
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                        .map(date -> new Birthday(date.toLocalDate()))
                        .orElse(null);
    }
    
}
