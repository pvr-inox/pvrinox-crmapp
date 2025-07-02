package com.cinema.crm.databases.pvrinox.entities;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "location_cinema")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, unique = true)
    //@Schema(example = "INOX-SHOWBIZ-200", description = "Unique Key for Theater CHAIN-POS-THEATER")
    private String uniqueKey;
    @Column(length = 100)
    //@Schema(example = "PVR Cinemas")
    private String name;
    @Column(length = 10)
   // @Schema(example = "SHOWBIZ", description = "POS Name", allowableValues = {"SHOWBIZ", "VISTA"})
    private String pos;
    @Column(length = 10)
    //@Schema(example = "PVR")
    private String chainKey;
    //@Schema(example = "1")
    private Integer cityId;
    @Column(length = 50)
    private String cityName;
    @ColumnDefault("30000")
    private Integer timeout;
    @Column(length = 100)
    private String subcity;
    @Column(length = 8)
    //@Schema(example = "200", description = "Showbiz thearter id")
    private String theatreId;
    @Column(length = 35)
    @JsonIgnore
    private String accNo;
    @Column(length = 15)
    @JsonIgnore
    private String serviceTaxNo;
    @Column(length = 200)
    private String address1;
    @Column(length = 200)
    private String address2;
    @Column(length = 200)
    private String address3;
    @Column(length = 25)
   // @Schema(example = "28.0")
    private String latitude;
    @Column(length = 25)
   // @Schema(example = "77.0")
    private String longitude;
    @Column(length = 30)
    @JsonIgnore
    private String telephone;
    @Column(length = 50)
    private String relationManager;
    @Column(length = 10)
    @JsonIgnore
    private String pgTheatreId;
    @JsonIgnore
    private Boolean paperLess;
    @JsonIgnore
    private Boolean fbDeliveryOnSeat;
    @Column(length = 50)
    private String caretakerContactNo;
    @Column(length = 100)
    private String businessPlace;
    @Column(length = 15)
    private String gstnNo;
    @Column(length = 35)
    @JsonIgnore
    private String cinNo;
    @Column(length = 200)
    private String emailId;
    private Integer stopTimeInMin;
    @Column(length = 100)
    private String alertTxt;
    private Boolean foodAvailable;
    @JsonIgnore
    private Integer distancingType=0;
    @JsonIgnore
    private Boolean threeDExcl;
    @JsonIgnore
    private Boolean dynamicPricing;
    @JsonIgnore
    private Boolean delTrack;
    private Boolean cancelBooking;
    @Column(length = 10)
    @JsonIgnore
    private String convHSNCode;
    @Column(length = 200)
    private String vistaUrl;
    @Column(length = 200)
    private String vistaUrl2;
    @Column(length = 30)
    private String stateName;
    @Column(length = 10)
    private String convFeeType;//OLD,NEW
    private Boolean screenTop;
    @Column(columnDefinition = "json")
    @Convert(converter = Cinema.LicenceConverter.class)
    private Map<String, Licence> vistaLicence;    // vista
//    @Column(columnDefinition = "json")
//    @Convert(converter = Cinema.ScreenConverter.class)
//    private Map<Integer, Screen> screens; // Converted to/from JSON vista
    private Date refreshAt;
    private Boolean syncStatus;
    @Column(columnDefinition = "TEXT")
    private String errData;
    @Column(columnDefinition = "TEXT")
    private String foodErrData;
    private Boolean active;
    private Boolean tempInActive;
    private Boolean incinema;
    private Boolean handicapRamp;
    private Boolean handicap;
    @Column(length = 100)
    private String incinemaFeatures;
    @Column(columnDefinition = "TEXT")
    private String tnc;
    @Column(length = 150)
    private String qrBookingUrl;
    @Column(length = 150)
    private String qrFoodUrl;
    @Column(length = 20)
    private String sapCode;
    @Column(length = 20)
    private String branchCode;
    @Column(length = 20)
    private String brand;
    @Column(length = 20)
    private String vistaHO;
    @Column(length = 10)
    private String hoCode;
    @Column(length = 15)
    private String erpLocation;
    @Column(length = 200)
    private String imageVertical;
    @Column(length = 200)
    private String imageHorizontal;
    @Column(length = 20)
    private String oldCinemaCode;
    @Column(columnDefinition = "TEXT")
    private String amenities;
    @Column(columnDefinition = "TINYINT default 0") private Boolean isPosAvailable  = false;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date modifiedAt;
    
    @Column(columnDefinition = "TINYINT default 0") private Boolean discountConnector = false;
    @Column(columnDefinition = "TINYINT default 0") private Boolean charge3DMsg;
    @Column(columnDefinition = "varchar(20) default ''") private String cinemaRadius = "";
    @Column(columnDefinition = "TINYINT default 0") private Boolean vakaao;
    
//    @Column(columnDefinition = "json")
//    @Convert(converter = Cinema.VakaaoScreenConverter.class)
//    private Map<Integer, VakaaoScreen> vakaaoScreens; // Converted to/from JSON vista
    
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Screen {
        private int screenId;
        private String screenName;
        private String screenType;
        private boolean handicap=false;
        //private boolean vakaao = false;
        private int occupancy = 0;
        private int admits = 0;
        private int minSeats = 5;
    }
    
//    @Getter
//    @Setter
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class VakaaoScreen {
//        private Integer screenId;
//        private String screenName;
//        private String screenType;
//        private Integer occupancy;
//        private Integer admits;
//        private long price;
//    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Licence {
        private String platform;
        private String licenceType;
        private String payType;
        private String code;
    }

    //Screen Convertor
//    @Converter(autoApply = true)
//    public static class ScreenConverter implements AttributeConverter<Map<Integer, Screen>, String> {
//        private final ObjectMapper objectMapper = new ObjectMapper();
//
//        @Override
//        public String convertToDatabaseColumn(Map<Integer, Screen> attribute) {
//            try {
//                return objectMapper.writeValueAsString(attribute);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException("Error converting JSON SCREEN to String", e);
//            }
//        }
//
//        @Override
//        public Map<Integer, Screen> convertToEntityAttribute(String dbData) {
//            if (dbData == null) {
//                return null; // Handle null values from the database
//            }
//            try {
//                return objectMapper.readValue(dbData, new TypeReference<Map<Integer, Screen>>() {
//                });
//            } catch (IOException e) {
//                throw new RuntimeException("Error converting String SCREEN to JSON", e);
//            }
//        }
//    }
    
  //Vakaao Screen Convertor
//    @Converter(autoApply = true)
//    public static class VakaaoScreenConverter implements AttributeConverter<Map<Integer, VakaaoScreen>, String> {
//        private final ObjectMapper objectMapper = new ObjectMapper();
//
//        @Override
//        public String convertToDatabaseColumn(Map<Integer, VakaaoScreen> attribute) {
//            try {
//                return objectMapper.writeValueAsString(attribute);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException("Error converting JSON VAKAAO SCREEN to String", e);
//            }
//        }
//
//        @Override
//        public Map<Integer, VakaaoScreen> convertToEntityAttribute(String dbData) {
//            if (dbData == null) {
//                return null; // Handle null values from the database
//            }
//            try {
//                return objectMapper.readValue(dbData, new TypeReference<Map<Integer, VakaaoScreen>>() {
//                });
//            } catch (IOException e) {
//                throw new RuntimeException("Error converting String VAKAAO SCREEN to JSON", e);
//            }
//        }
//    }

    //Licence Convertor
    @Converter(autoApply = true)
    public static class LicenceConverter implements AttributeConverter<Map<String, Licence>, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<String, Licence> attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON Licence to String", e);
            }
        }

        @Override
        public Map<String, Licence> convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<Map<String, Licence>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error converting String Licence to JSON", e);
            }
        }
    }


    //Class Convertor
    @Converter(autoApply = true)
    public static class LayoutConverter implements AttributeConverter<Map<String, INScreenVO>, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<String, INScreenVO> attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting JSON Layout to String", e);
            }
        }

        @Override
        public Map<String, INScreenVO> convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null; // Handle null values from the database
            }
            try {
                return objectMapper.readValue(dbData, new TypeReference<Map<String, INScreenVO>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Error converting String Layout to JSON", e);
            }
        }
    }


}
