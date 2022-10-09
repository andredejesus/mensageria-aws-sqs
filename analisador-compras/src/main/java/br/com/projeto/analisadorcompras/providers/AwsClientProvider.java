package br.com.projeto.analisadorcompras.providers;

import br.com.projeto.analisadorcompras.config.AwsSQSConfig;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;


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
    public void criaFilaSolicitacoesComprasAprovadas(){

        CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest(awsSQSConfig.getFilaComprasAprovadas());
        String standardQueueUrl = buildAmazonSQSAsync().createQueue(createStandardQueueRequest).getQueueUrl();
        log.info("Fila Criada de Compras aprovadas - {}", standardQueueUrl);

    }

}
