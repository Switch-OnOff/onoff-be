package com.switchteam.onoff.domain.property.service;

import com.switchteam.onoff.domain.property.dto.KakaoAddressResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class KakaoGeocodingService {

    private final WebClient kakaoWebClient;

    public KakaoGeocodingService(WebClient kakaoWebClient) {
        this.kakaoWebClient = kakaoWebClient;
    }

    public Optional<LatLng> geocode(String roadAddress) {
        KakaoAddressResponse res = kakaoWebClient.get()
                .uri(uriBuilder -> buildAddressSearchUri(uriBuilder, roadAddress))
                .retrieve()
                .bodyToMono(KakaoAddressResponse.class)
                .block();

        if (res == null || res.documents() == null || res.documents().isEmpty()) {
            return Optional.empty();
        }

        KakaoAddressResponse.Document doc = res.documents().get(0);
        try {
            double lng = Double.parseDouble(doc.x()); // x = 경도
            double lat = Double.parseDouble(doc.y()); // y = 위도
            return Optional.of(new LatLng(lat, lng));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private URI buildAddressSearchUri(UriBuilder b, String query) {
        return b.path("/v2/local/search/address.json")
                .queryParam("query", query)
                .build();
    }

    public record LatLng(double lat, double lng) {}
}