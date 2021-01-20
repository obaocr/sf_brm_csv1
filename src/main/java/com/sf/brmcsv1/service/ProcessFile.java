package com.sf.brmcsv1.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProcessFile {

    public static Map<String, String> getMapInspIdFromFile(String filePath) {
        List<List<String>> result = new ArrayList<>();
        Map<String, String> mapInspId = new TreeMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                result.add(Arrays.asList(values));
                if (!values[0].equals("ID")) {
                    String inspecteur = values[5].substring(19, values[5].indexOf("-") - 1).toUpperCase();
                    mapInspId.put(inspecteur, values[0]);
                }
            }
            return mapInspId;
        } catch (Exception e) {
            System.out.println("exception = " + e.toString());
            return null;
        }
    }

    public static List<List<String>> setIdToCsvFile(String filePath, Map<String, String> mapInspId, String fileOut) {
        List<List<String>> result = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = new ArrayList<>();
            String[] values;
            char sep = '-';
            while ((values = csvReader.readNext()) != null) {

                if (!values[0].equals("scPrincipal__c")) {
                    List<Integer> idxList = new ArrayList<>();
                    char[] ch = values[4].toCharArray();

                    Integer i=0;
                    for (char c : ch) {
                        if (Character.compare(c,sep) == 0) {
                            idxList.add(i);
                        }
                        i++;
                    }
                    int posdep = idxList.get(idxList.size()-2);
                    int posfin = idxList.get(idxList.size()-1);
                    String inspecteur = values[4].substring(posdep + 2, posfin-1).toUpperCase();
                    String inspId = mapInspId.get(inspecteur);
                    System.out.println("inspecteur fic 2 & id  =" + inspecteur + "/" + inspId);
                    if (inspId != null) {
                        values[0] = inspId;
                    }
                    lines.add(values);
                } else {
                    lines.add(values);
                }
                result.add(Arrays.asList(values));
            }
            String STRING_ARRAY_SAMPLE = fileOut;
            Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            for (String[] line : lines) {
                csvWriter.writeNext(line);
            }
            return result;
        } catch (Exception e) {
            System.out.println("exception = " + e.toString());
            return null;
        }
    }

}
