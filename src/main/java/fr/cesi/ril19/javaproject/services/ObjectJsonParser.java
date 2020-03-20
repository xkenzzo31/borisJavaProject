package fr.cesi.ril19.javaproject.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import fr.cesi.ril19.javaproject.models.project.Tache;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectJsonParser {

    public static CsvMapper csvMapper;
    public static CsvSchema schema;
    public static ObjectMapper mapper;

    public ObjectJsonParser() {
        csvMapper = new CsvMapper();
        schema = CsvSchema.emptySchema();
    }

    public static List<Object> parseFromCSV(InputStreamReader stream, Class<?> objectToParse) throws IOException {
        schema = csvMapper.schemaFor(objectToParse).withHeader().withColumnReordering(true);
        ObjectReader reader = csvMapper.readerFor(objectToParse).with(schema);
        Object o = reader.<Object>readValues(stream).readAll();
        return (List<Object>) o;
    }

    public static List<Tache> parseTaskFromJSONString(String string) throws JsonProcessingException {
        ObjectNode node = new ObjectMapper().readValue(string, ObjectNode.class);
        List<Tache> lt = new ArrayList<Tache>();
        for (JsonNode task : node.get("tasks")){
            lt.add(new Tache(task.get("num").toString(),task.get("hourCost").intValue(),task.get("duration").intValue()));
        }
        return lt;
    }

    public static List<Tache> parseTaskFromXML(String string) throws JsonProcessingException {
        throw new UnsupportedOperationException();
        // TODO:
    }
}
