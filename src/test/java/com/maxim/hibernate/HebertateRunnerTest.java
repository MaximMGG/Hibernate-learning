package com.maxim.hibernate;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.maxim.hibernate.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

public class HebertateRunnerTest {
    
    @Test
    void checkRflectionAip() throws SQLException, IllegalArgumentException, IllegalAccessException {

        User user = User.builder()
                            .username("Poko123")
                            .firstname("Pok")
                            .lastname("Mok")
                            .birthDate(LocalDate.of(1999, 11, 1))
                            .age(14)
                            .build();

                            
                            String sql = """
                                insert
                                into
                                %s
                                (%s)
                                values
                                (%s)
                                """;
        Class<? extends User> userApi = user.getClass();
        String tableName = Optional.ofNullable(userApi.getAnnotation(Table.class))
                                        .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                                        .orElse(user.getClass().getName());
        Field[] declaredFields = userApi.getDeclaredFields();
        String columnsName = Arrays.stream(declaredFields)
                            .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                                .map(Column::name)
                                .orElse(field.getName()))
                            .collect(Collectors.joining(", "));
        String columnValue = Arrays.stream(declaredFields)
                    .map(field -> "?")
                    .collect(Collectors.joining(", "));
        System.out.println(sql.formatted(tableName, columnsName, columnValue));
        
        Connection connecion = null;
        
        PreparedStatement prepareStatement = connecion.prepareStatement(sql.formatted(tableName, columnsName, columnValue));
        for(Field field : declaredFields) {
            field.setAccessible(true);
            prepareStatement.setObject(1, field.get(user));
        }
    }
}

