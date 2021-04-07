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
        final String file1 = "C:/_Temp_SF/_MEP_20210309/BRM_01_INSERT_SuiviCommercial_PlanAction_Inspecteur_Gen_Success.csv";
        // Fichier en entrée pour peupler les Id
        final String file2 = "C:/_Temp_SF/_MEP_20210309/BRM_02_INSERT_SuiviCommercial_PlanAction_Courtier_Gen.csv";
        // Nom du fichier de sortie
        final String fileOut = "C:/_Temp_SF/_MEP_20210309/BRM_02_INSERT_SuiviCommercial_PlanAction_Courtier_Gen_Final.csv";

        Map<String, String> mapInspId = ProcessFile.getMapInspIdFromFile(file1);
        System.out.println("mapInspId = " + mapInspId);
        List<List<String>> result = ProcessFile.setIdToCsvFile(file2, mapInspId, fileOut);
        System.out.println("result lines = " + result.size());
    }
}
