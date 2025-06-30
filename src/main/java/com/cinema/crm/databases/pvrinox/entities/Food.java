package com.cinema.crm.databases.pvrinox.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Food {
    private String name = "";
    private String itemId = "";
    
    @Positive
    private int quantity = 0;
    // @JsonSerialize(using = RupeeFormatSerializer.class)
    @Positive
    private long price = 0;
    private boolean veg = false;
    private int foodType = 0;
    private List<AddOn> addons; // added for fnb
    private Integer itemType;
    private String parentId;
    
    
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddOn {
    	private String name;
    	@Positive
        private long price;
        private String itemId;
        @Positive
        private int quantity;

    }

}
