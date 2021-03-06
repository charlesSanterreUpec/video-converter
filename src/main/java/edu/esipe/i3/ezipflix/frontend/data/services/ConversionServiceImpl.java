package edu.esipe.i3.ezipflix.frontend.data.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import edu.esipe.i3.ezipflix.frontend.data.entities.VideoConversions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ConversionServiceImpl implements ConversionService {

    @Value("${pubsub.project}")
    String project;
    @Value("${pubsub.topic}")
    String topic;
    @Value("${aws.dynamodb.table}")
    String table;

    private String result;

    @Override
    public String publish(VideoConversions video) throws Exception {
        ProjectTopicName topicName = ProjectTopicName.of(project, topic);
        Publisher publisher = null;


        try {
            publisher = Publisher.newBuilder(topicName).build();
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(video);

            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            ApiFuture<String> future = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(
                    future,
                    new ApiFutureCallback<String>() {

                        @Override
                        public void onFailure(Throwable throwable) {
                            if (throwable instanceof ApiException) {
                                ApiException apiException = ((ApiException) throwable);
                                throw apiException;
                            }
                        }

                        @Override
                        public void onSuccess(String messageId) {
                            result = messageId;
                        }
                    },
                    MoreExecutors.directExecutor());
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
        return result;
    }

    @Override
    public String save(VideoConversions video) {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_3)
                .build();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable(this.table);
        Item item = new Item()
                .withPrimaryKey("uuid", video.getUuid().toString())
                .withString("originpath", video.getOriginPath().toString())
                .withString("targetpath", "target");

        PutItemOutcome itemOutcome = table.putItem(item);
        return itemOutcome.toString();
    }
}
