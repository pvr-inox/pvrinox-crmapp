package com.cinema.crm.databases.pvrinox.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "location_pos")
public class Pos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    //@Schema(example = "SHOWBIZ", description = "Cinema Chain Key", allowableValues = {"SHOWBIZ", "VISTA"})
    private String name;

    private Long chainId;

    @Column(length = 10)
    private String chainKey;

    @Column(length = 100)
    private String showBizContentUrl;

    @Column(length = 100)
    private String showBizBookingUrl;

    @Column(columnDefinition = "json")
    @Convert(converter = PosShowBizPartnerConverter.class)
    private Map<String, PosShowBizPartner> showBizPartner; // Converted to/from JSON
    private boolean importCinemas;
    private boolean active;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date modifiedAt;

    @Getter
    @Setter
    public static class PosShowBizPartner {
        private String platform;
        private String partnerId;
        private String pass;
    }

    @Converter(autoApply = true)
    public static class PosShowBizPartnerConverter implements AttributeConverter<Map<String, PosShowBizPartner>, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<String, PosShowBizPartner> attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON to String", e);
            }
        }

        @Override
        public Map<String, PosShowBizPartner> convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<Map<String, PosShowBizPartner>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error converting String to JSON", e);
            }
        }
    }
}
