package org.example.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Historic;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class HistoricService {
    private List<Historic> readHistories(String filePath) throws IOException {
        File file = new File(
                filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        List<Historic> result = mapper.readValue(file, new TypeReference<List<Historic>>() {
        });
        Collections.sort(result);
        return result;
    }

    private void writeHistories(String filePath, List<Historic> result) throws IOException {
        File file = new File(
                filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        Collections.sort(result);
        mapper.writeValue(file, result);
    }
}
