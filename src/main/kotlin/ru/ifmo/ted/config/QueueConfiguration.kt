package ru.ifmo.ted.config

import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
        connectionFactory.setHost("localhost")
        connectionFactory.setPort(5672)
        return SingleConnectionFactory(connectionFactory)
    }

    @Bean
    fun jmsListenerContainerFactory(): JmsListenerContainerFactory<*>? {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(jmsConnectionFactory())
        return factory
    }
}
