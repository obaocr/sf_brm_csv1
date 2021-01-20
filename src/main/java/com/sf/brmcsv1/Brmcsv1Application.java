package com.sf.brmcsv1;

import com.opencsv.exceptions.CsvValidationException;
import com.sf.brmcsv1.service.ReadFile1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Map;

@SpringBootApplication
public class Brmcsv1Application {

    public static void main(String[] args)  {
        SpringApplication.run(Brmcsv1Application.class, args);
        final String file1 = "C:/Temp_SF/_MEP_20210204/BRM-01-INSERT-SuiviCommercial-PlanAction_Inspecteur_Pilote_success.csv";
        final String file2 = "C:/Temp_SF/_BRM-02-INSERT-SuiviCommercial-Plan_Action_Courtier_Pilote.csv";

        Map<String, String> mapInspId = ReadFile1.getMapInspId(file1);
        System.out.println("mapInspId = " + mapInspId);
    }

}
