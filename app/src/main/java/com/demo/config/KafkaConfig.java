package com.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;

@Configuration
public class KafkaConfig {
    @Value("${kafka.topics.request}")
    private String requestTopic;

    /** Partition count for internal generation. */
    @Value("${kafka.properties.partition.count}")
    private int topicPartitionCount;

    /** Replication factor for internal generation. */
    @Value("${kafka.properties.replication.factor}")
    private int topicReplicationFactor;

    /** Max message retry attempt for internal configuration. */
    @Value("${kafka.properties.message.max.attempt}")
    private int maxAttempt;

    /** Retry naming pattern for internal configuration. */
    @Value("${kafka.properties.retry.naming.pattern}")
    private String retryNamingPattern;

    /** Dead letter topic naming pattern for internal configuration. */
    @Value("${kafka.properties.dlt.naming.pattern}")
    private String dltNamingPattern;

    /** Delay Constant. */
    public static final long TOPIC_DELAY_MILLISECONDS = 1000;

    /** Max delay Constant. */
    public static final long MAX_DELAY_MILLISECONDS = 30000;

    /** Delay multiplier. */
    public static final long TOPIC_DELAY_MULTIPLIER = 3;

    @Bean
    public NewTopic requestKafkaTopic() {
        return TopicBuilder.name(requestTopic)
                    .partitions(topicPartitionCount)
                    .replicas(topicReplicationFactor)
                    .build();
    }

    /**
     * Configure retry topics.
     * @param template message template
     * @return RetryTopicConfiguration
     */
    @Bean
    public RetryTopicConfiguration retry(final KafkaTemplate<String, Object> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .exponentialBackoff(TOPIC_DELAY_MILLISECONDS, TOPIC_DELAY_MULTIPLIER, MAX_DELAY_MILLISECONDS)
                .retryTopicSuffix(retryNamingPattern)
                .dltSuffix(dltNamingPattern)
                .dltHandlerMethod("exampleRequestConsumer", "dlt")
                .autoCreateTopics(true, topicPartitionCount, (short) topicReplicationFactor)
                .setTopicSuffixingStrategy(TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
                .maxAttempts(maxAttempt)
                .create(template);
    }
}
