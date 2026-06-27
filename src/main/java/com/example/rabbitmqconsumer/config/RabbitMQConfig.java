package com.example.rabbitmqconsumer.config;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "alerta-exchange";
    // CAMBIO: Ahora usamos la cola para JSON
    public static final String QUEUE_JSON = "Cola-Alerta-JSON";

    @Bean
    public FanoutExchange alertaExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    // CAMBIO: Declaramos la cola JSON
    @Bean
    public Queue colaAlertaJSON() {
        return new Queue(QUEUE_JSON);
    }

    // CAMBIO: Vinculamos la cola JSON al exchange
    @Bean
    public Binding bindingJSON(FanoutExchange alertaExchange, Queue colaAlertaJSON) {
        return BindingBuilder.bind(colaAlertaJSON).to(alertaExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        // Asegúrate de incluir el paquete del productor en los paquetes confiables
        classMapper.setTrustedPackages("com.example.rabbitmqproductor.dto",
                "com.example.rabbitmqconsumer.dto");

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.example.rabbitmqproductor.dto.AlertaRequest", AlertaRequest.class);
        classMapper.setIdClassMapping(idClassMapping);

        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);

        return factory;
    }
}
