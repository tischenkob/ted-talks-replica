package ru.ifmo.ted.config

import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.JmsListenerContainerFactory
import org.springframework.jms.connection.SingleConnectionFactory
import javax.jms.ConnectionFactory

@Configuration
class QueueConfiguration {
    @Bean
    fun jmsConnectionFactory(): ConnectionFactory {
        val connectionFactory = RMQConnectionFactory()
        connectionFactory.setUsername("guest")
        connectionFactory.setPassword("guest")
        connectionFactory.setVirtualHost("/")
        connectionFactory.setHost("rabbitmq")
        connectionFactory.setPort(5672)
        return SingleConnectionFactory(connectionFactory)
    }

    @Bean
    @Profile("service")
    fun jmsListenerContainerFactory(): JmsListenerContainerFactory<*>? {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(jmsConnectionFactory())
        return factory
    }
}
