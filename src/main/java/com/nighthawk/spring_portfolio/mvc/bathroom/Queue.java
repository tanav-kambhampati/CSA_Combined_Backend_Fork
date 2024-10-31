package com.nighthawk.spring_portfolio.mvc.bathroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Convert;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
public class Queue {
    @Id
    private Long studentId;
    
    @Column(unique = true)
    private String studentName;
    
    @Convert(converter = QueuePositionsConverter.class)
    private Map<String, Integer> queuePositions = new HashMap<>();

    // Custom constructor
    public Queue(Long studentId, String studentName, Map<String, Integer> queuePositions) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.queuePositions = queuePositions;
    }
}

@Converter
class QueuePositionsConverter implements AttributeConverter<Map<String, Integer>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Integer> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            return "{}";
        }
    }

    @Override
    public Map<String, Integer> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Map<String, Integer>>() {});
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}