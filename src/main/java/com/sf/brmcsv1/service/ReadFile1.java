package com.sf.brmcsv1.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadFile1 {

    public static Map<String,String> getMapInspId(String filePath) {
        List<List<String>> result = new ArrayList<>();
        Map<String,String> mapInspId = new TreeMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                result.add(Arrays.asList(values));
                if(!values[0].equals("ID")) {
                    String inspecteur = values[5].substring(19,values[5].indexOf("-") - 1);
                    mapInspId.put(inspecteur,values[0]);
                }
            }
            return mapInspId;
        } catch (Exception e) {
            System.out.println("exception = " + e.toString());
            return null;
        }
    }
}
