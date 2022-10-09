package br.com.projeto.comprasolicitada.config;

import com.amazonaws.auth.BasicAWSCredentials;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AwsSQSConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Value("${cloud.aws.fila.url_compras_solicitadas}")
    private String urlFilaComprasSolicitadas;

    @Value("${cloud.aws.fila.compras_solicitadas}")
    private String filaComprasSolicitadas;

    @Value("${cloud.aws.region.static}")
    public String regiao;

    @Value("${cloud.aws.url_aws}")
    private String urlAws;

    public BasicAWSCredentials credentials(){
        return new BasicAWSCredentials(awsAccessKey,awsSecretKey);
    }






}
