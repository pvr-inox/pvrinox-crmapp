package com.cinema.crm.modules.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class RupeeFormatSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        double amountInRupees = value / 100.0;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String formattedAmount = df.format(amountInRupees);
        jsonGenerator.writeString(formattedAmount);
    }
}
