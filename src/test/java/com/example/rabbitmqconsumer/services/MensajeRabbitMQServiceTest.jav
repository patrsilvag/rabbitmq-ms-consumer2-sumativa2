package com.example.rabbitmqconsumer.services;

import com.example.rabbitmqconsumer.models.MensajeRabbitMQ;
import com.example.rabbitmqconsumer.repositories.MensajeRabbitMQRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MensajeRabbitMQServiceTest {

    @Mock
    private MensajeRabbitMQRepository repository;

    @InjectMocks
    private MensajeRabbitMQService service;

    @Test
    void testGuardarMensaje_CreaEntidadCorrecta() {
        String contenido = "Test Oracle";
        
        service.guardarMensaje(contenido);
        
        ArgumentCaptor<MensajeRabbitMQ> captor = ArgumentCaptor.forClass(MensajeRabbitMQ.class);
        verify(repository).save(captor.capture());
        
        assertEquals("RECIBIDO", captor.getValue().getEstado());
        assertEquals(contenido, captor.getValue().getContenido());
    }
}