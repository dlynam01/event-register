package com.fourtheorem.ecs.demo.eventsite.register.eventregister.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsiteConfig {
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

//    @Bean
//    public AmazonDynamoDB amazonDynamoDB() {
//        AmazonDynamoDB amazonDynamoDB
//                = new AmazonDynamoDBClient(amazonAWSCredentials());
//
//        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
//            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
//        }
//
//        return amazonDynamoDB;
//    }
//
//    @Bean
//    public AWSCredentials amazonAWSCredentials() {
//        return new BasicAWSCredentials(
//                amazonAWSAccessKey, amazonAWSSecretKey);
//    }
}
