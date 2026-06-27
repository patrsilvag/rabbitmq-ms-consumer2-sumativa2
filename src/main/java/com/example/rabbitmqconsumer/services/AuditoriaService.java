package com.example.rabbitmqconsumer.services;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AuditoriaService {

    // Asegúrate de que esta carpeta exista en tu sistema o en el contenedor Docker
    private final String RUTA_AUDITORIA = "auditoria_json/";

    public void guardarComoJson(AlertaRequest alerta) {
        try {
            // 1. Crear el directorio si no existe
            Files.createDirectories(Paths.get(RUTA_AUDITORIA));

            // 2. Crear un nombre de archivo basado en el nombre del paciente y la hora
            String timestamp =
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = RUTA_AUDITORIA + "alerta_"
                    + alerta.getNombrePaciente().replaceAll("\\s+", "") + "_" + timestamp + ".json";

            // 3. Configurar ObjectMapper para manejar fechas correctamente
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Necesario si AlertaRequest usa
                                                         // LocalDateTime
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nombreArchivo), alerta);

            System.out.println("Archivo JSON generado con éxito en: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al intentar escribir el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
