package com.hseea.jsresource.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class ElasticSearchConfig {

    @Value("custom.elasticsearch.nodes")
    private String elasticsearchNodes;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        List<HttpHost> httpHosts = new ArrayList<>();
        String[] nodes = elasticsearchNodes.split(",");
        for(String node : nodes){
            String ip = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            httpHosts.add(new HttpHost(ip,port,"http"));
            log.info("add elasticsearch node, ip: {} port: {}",ip,port);
        }
        return new RestHighLevelClient(RestClient.builder(httpHosts.toArray(new HttpHost[0])));
    }
}
