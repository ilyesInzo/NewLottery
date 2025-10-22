package org.example.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext context) throws IOException {
        if (p.getValueAsString() != null) {
            return LocalDate.parse(p.getValueAsString(), fmt);
        }
        System.out.println("Could not local date deserialize " + p.getValueAsString());
        return null;
    }

}