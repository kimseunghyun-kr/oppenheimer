package com.stock.oppenheimer.indicators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class VolumeProfilePythonAdapter {

    //    String databaseContext = "h2";
//    String indicatorAction = "1";
//    String target = "1";
//    String additionalArg1 = "value1";
//    String additionalArg2 = "value2";

    public void runPythonScript(String databaseContext, String indicatorAction, String target, String... additionalArgs) {

        try {
            String pythonScriptPath = "./src/main/java/com/stock/oppenheimer/indicators/KDE.py";
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, databaseContext, indicatorAction, target, List.of(additionalArgs).toString());
            processBuilder.redirectErrorStream(true);
            Process process;

            process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            log.info(output.toString());
            // Wait for the process to finish
            int exitCode = process.waitFor();
            log.info("Python script exited with code: {}", exitCode);

        } catch (IOException | InterruptedException e) {
            log.error("error with:     ", e);
            throw new RuntimeException(e);
        }
    }
}

