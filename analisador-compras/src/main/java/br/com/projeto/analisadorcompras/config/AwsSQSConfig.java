package br.com.projeto.analisadorcompras.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AwsSQSConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Value("${cloud.aws.fila.url_compras_solicitadas}")
    private String urlFilaComprasSolicitadas;

    @Value("${cloud.aws.fila.compras_solicitadas}")
    private String filaComprasSolicitadas;

    @Value("${cloud.aws.fila.solicitacoes_compras_aprovadas}")
    private String filaComprasAprovadas;

    @Value("${cloud.aws.fila.url_solicitacoes_compras_aprovadas}")
    private String urlSolicitacoesComprasAprovadas;

    @Value("${cloud.aws.region.static}")
    public String regiao;

    @Value("${cloud.aws.url_aws}")
    private String urlAws;

    public BasicAWSCredentials credentials(){
        return new BasicAWSCredentials(awsAccessKey,awsSecretKey);
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(){
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey,awsSecretKey)))
                .build();
    }

}
