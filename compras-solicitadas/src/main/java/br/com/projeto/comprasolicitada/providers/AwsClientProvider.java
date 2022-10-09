package br.com.projeto.comprasolicitada.providers;

import br.com.projeto.comprasolicitada.config.AwsSQSConfig;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class AwsClientProvider implements AwsProvider {

    @Autowired
    private AwsSQSConfig awsSQSConfig;

    @Bean
    @Primary
    @Override
    public AmazonSQSAsync buildAmazonSQSAsync() {

        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                awsSQSConfig.getUrlAws(),
                                awsSQSConfig.getRegiao()
                        )
                )
                .withCredentials(new AWSStaticCredentialsProvider(
                        awsSQSConfig.credentials()
                )).build();

    }

    @Bean
    public void criaFilaCompraCartaoCredito(){

        CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest(awsSQSConfig.getFilaComprasSolicitadas());
        String standardQueueUrl = buildAmazonSQSAsync().createQueue(createStandardQueueRequest).getQueueUrl();
        log.info("Fila Criada - {}", standardQueueUrl);

    }

//    @Bean
//    public void criaFilaFifo(){
//        Map<String, String> queueAttributes = new HashMap<>();
//        queueAttributes.put("FifoQueue", "true");
//        queueAttributes.put("ContentBasedDeduplication", "true");
//
//        CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest("compra_cartao_credito.fifo")
//                .withAttributes(queueAttributes);
//
//        String fifoQueueUrl = buildAmazonSQSAsync().createQueue(createFifoQueueRequest).getQueueUrl();
//        log.info("Fila Criada - {}", fifoQueueUrl);
//    }

}
