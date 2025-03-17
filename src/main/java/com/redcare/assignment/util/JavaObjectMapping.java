package com.redcare.assignment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;

public class JavaObjectMapping {

    public static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // List.of(1,2,3) will be converted to 1,2,3 instead of [1,2,3]
    public static <T> String convertListAsJsonArrayWithoutArrayBrackets(List<T> list){
        try {
            final String fullJsonArray = objectMapper.writeValueAsString(list);
            return fullJsonArray.substring(1,fullJsonArray.length()-1);
        }catch (Exception ex){
            throw new RuntimeException("Exception while converting list to a json", ex);
        }
    }
}
