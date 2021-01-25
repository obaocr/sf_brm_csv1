package com.sf.brmcsv1.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProcessFile {

    /**
     *
     * @param filePath pour le fichier des plans actions inspecteur
     * @return map des inspecteur  + Id plan action inspecteur
     */
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

    /**
     *
     * @param filePath fichier final suivi commercial complete des idParent plan action inspecteur
     * @param mapInspId
     * @param fileOut
     * @return
     * parcours du fichier, on écarte la ligne header de l'analmyse
     *  on calcule extrait l'inspecteur du champ name(4), encadré par "-" à partir de la fin
     *  on cherche l'id pour cet inspecteur puis on comple le fichier (champ0)
     */
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
                        if (Character.valueOf(c) == Character.valueOf('-')) {
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
            csvWriter.close();
            System.out.println("lines = " + lines.size());
            System.out.println("result = " + result.size());
            return result;
        } catch (Exception e) {
            System.out.println("exception = " + e.toString());
            return null;
        }
    }

}
