package br.com.fiap.mvp.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

public class TestConfigTest {

    private final Map<String, String> previousProperties = new HashMap<>();
    private final Set<String> configuredProperties = new HashSet<>();

    @After
    public void restoreProperties() {
        configuredProperties.forEach(propertyName -> {
            String previousValue = previousProperties.get(propertyName);
            if (previousValue == null) {
                System.clearProperty(propertyName);
                return;
            }

            System.setProperty(propertyName, previousValue);
        });
    }

    @Test
    public void devePermitirSobrescreverParametrosConfiguraveisPorPropriedadesDoSistema() {
        override("base.url", "http://api.local.test");
        override("citizen.id", "cidadao-automatizado");
        override("slug", "dica-automatizada");
        override("collection.point.id", "ponto-automatizado");
        override("address", "Rua dos Testes, Sao Paulo");
        override("lat", "-23.5505");
        override("lng", "-46.6333");
        override("max.distance.meters", "1500");

        assertEquals("http://api.local.test", TestConfig.baseUrl());
        assertEquals("cidadao-automatizado", TestConfig.citizenId());
        assertEquals("dica-automatizada", TestConfig.slug());
        assertEquals("ponto-automatizado", TestConfig.collectionPointId());
        assertEquals("Rua dos Testes, Sao Paulo", TestConfig.address());
        assertEquals(-23.5505, TestConfig.lat(), 0.0001);
        assertEquals(-46.6333, TestConfig.lng(), 0.0001);
        assertEquals(1500, TestConfig.maxDistanceMeters());
    }

    @Test
    public void deveLerParametrosConfiguraveisRecebidosPeloMaven() {
        assumeTrue(Boolean.getBoolean("config.override.probe"));

        assertEquals("http://api.maven.test", TestConfig.baseUrl());
        assertEquals("cidadao-maven", TestConfig.citizenId());
        assertEquals("slug-maven", TestConfig.slug());
        assertEquals("ponto-maven", TestConfig.collectionPointId());
        assertEquals("Endereco Maven, Sao Paulo", TestConfig.address());
        assertEquals(-22.9000, TestConfig.lat(), 0.0001);
        assertEquals(-43.2000, TestConfig.lng(), 0.0001);
        assertEquals(2500, TestConfig.maxDistanceMeters());
    }

    private void override(String propertyName, String value) {
        if (!configuredProperties.contains(propertyName)) {
            previousProperties.put(propertyName, System.getProperty(propertyName));
        }

        configuredProperties.add(propertyName);
        System.setProperty(propertyName, value);
    }
}
