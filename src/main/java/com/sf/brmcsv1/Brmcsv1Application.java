package com.sf.brmcsv1;

import com.sf.brmcsv1.service.ProcessFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Brmcsv1Application {

    public static void main(String[] args) {
        SpringApplication.run(Brmcsv1Application.class, args);
        // Fichier en entrée avec les Id
        final String file1 = "C:/Temp_SF/_MEP_20210204/BRM-01-INSERT-SuiviCommercial-PlanAction_Inspecteur_Pilote_success.csv";
        // Fichier en entrée pour peupler les Id
        final String file2 = "C:/Temp_SF/_MEP_20210204/BRM-02-INSERT-SuiviCommercial-Plan_Action_Courtier_Pilote.csv";
        // Nom du fichier de sortie
        final String fileOut = "C:/Temp_SF/_MEP_20210204/BRM-02-INSERT-SuiviCommercial-Plan_Action_Courtier_Pilote_Final.csv";

        Map<String, String> mapInspId = ProcessFile.getMapInspIdFromFile(file1);
        System.out.println("mapInspId = " + mapInspId);
        List<List<String>> result = ProcessFile.setIdToCsvFile(file2, mapInspId, fileOut);
        System.out.println("result = " + result);

    }

}
