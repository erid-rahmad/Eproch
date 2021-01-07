package com.bhp.opusb.service;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.service.dto.BhinnekaProductFeedDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class BhinnekaProductService implements MarketplaceProductService<BhinnekaProductFeedDTO> {
  
  private final WebClient webClient;

  public BhinnekaProductService(ApplicationProperties properties) {
    final String url = properties.getIntegration().getMarketplace().getBhinneka().getProductFeedUrl();
    webClient = WebClient.create(url);
  }

  @Override
  public Mono<BhinnekaProductFeedDTO> findByCriteria() {
    return webClient.get().retrieve().bodyToMono(BhinnekaProductFeedDTO.class);
  }

}
