package com.example.rabbitmqconsumer.listeners;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import com.example.rabbitmqconsumer.services.AuditoriaService; // Cambiamos el servicio
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditoriaJSONConsumer {

    // Ahora inyectamos el servicio que maneja los archivos JSON
    private final AuditoriaService auditoriaService;

    // APUNTAMOS A LA COLA CORRECTA: Cola-Alerta-JSON
    @RabbitListener(queues = "Cola-Alerta-JSON")
    public void recibirMensaje(AlertaRequest alerta) {
        System.out
                .println("Procesando Alerta para Auditoría (JSON): " + alerta.getNombrePaciente());

        // Llamamos al método que genera el archivo en lugar de guardar en BD
        auditoriaService.guardarComoJson(alerta);
    }
}
