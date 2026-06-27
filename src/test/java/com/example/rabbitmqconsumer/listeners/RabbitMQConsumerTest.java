package com.example.rabbitmqconsumer.listeners;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import com.example.rabbitmqconsumer.services.AuditoriaService; // 1. Importamos el servicio correcto
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RabbitMQConsumerTest {

    @Autowired
    private AuditoriaJSONConsumer auditoriaConsumer; // Inyectamos el consumidor de auditoría

    @MockBean
    private AuditoriaService auditoriaService; // 2. Mockeamos el servicio de auditoría

    @Test
    void testConsumoMensaje_LlamaAlServicioDeAuditoria() {
        // Creamos el objeto AlertaRequest
        AlertaRequest alerta = AlertaRequest.builder().nombrePaciente("Test").habitacion("101")
                .colorAlerta("VERDE").estado("PENDIENTE").build();

        // 3. Llamamos al método que procesa el mensaje
        auditoriaConsumer.recibirMensaje(alerta);

        // 4. Verificamos que el servicio de auditoría fue llamado con el método guardarComoJson
        verify(auditoriaService).guardarComoJson(any(AlertaRequest.class));
    }
}
