package org.java.dev.service;

import org.java.dev.configuration.AppProperties;
import org.java.dev.configuration.LoggingConfiguration;
import org.java.dev.configuration.hibernate.Datasource;
import org.java.dev.dto.ClientDto;
import org.java.dev.dto.PlanetDto;
import org.java.dev.entity.ClientEntity;
import org.java.dev.entity.PlanetEntity;
import org.java.dev.mapper.ClientDtoMapper;
import org.java.dev.mapper.PlanetDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class CrudServiceTest {
    protected AppProperties appProperties;
    protected ClientCrudService clientCrudService;
    protected PlanetCrudService planetCrudService;

    @BeforeEach
    public void setup() {
        appProperties = AppProperties.load();
        new LoggingConfiguration();
        clientCrudService = new ClientCrudService(new Datasource());
        planetCrudService = new PlanetCrudService(new Datasource());
    }

    @Test
    void clientCrudServiceGetById() {
        String actual = clientCrudService.getById(1L).getName();
        String expected = "Bill";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void clientCrudServiceCreate() {
        ClientDto clientDto = ClientDto.of(null, "BigBob");
        ClientEntity clientEntity = ClientDtoMapper.instance().map(clientDto);
        ClientEntity actual = clientCrudService.create(clientEntity);
        Assertions.assertNotNull(actual);
    }

    @Test
    void clientCrudServiceDelete() {
        List<ClientEntity> clientEntityExpected = clientCrudService.findAll();
        int expected = clientEntityExpected.size() - 1;
        Long id = clientEntityExpected.stream().map(e -> e.getId()).max(Long::compare).get();
        clientCrudService.delete(id);
        List<ClientEntity> clientEntityActual = clientCrudService.findAll();
        int actual = clientEntityActual.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void clientCrudServiceUpdate() {
        String expected = "Elon " + LocalDateTime.now();
        ClientDto clientDto = ClientDto.of(2L, expected);
        ClientEntity clientEntity = ClientDtoMapper.instance().map(clientDto);
        clientCrudService.update(clientEntity);
        String actual = clientCrudService.getById(2L).getName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void planetCrudServiceGetById() {
        String actual = planetCrudService.getById("MARS").getName();
        String expected = "Mars";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void planetCrudServiceCreateDelete() {
        String planetID = "TESTCREATEDELETER";
        PlanetDto planetDto = PlanetDto.of(planetID, "CapEx 999");//planetID);
        PlanetEntity planetEntity = PlanetDtoMapper.instance().map(planetDto);
        System.out.println(" ===>>> planetDto = " + planetDto);
        planetCrudService.create(planetEntity);
        List<PlanetEntity> planetEntityExpected = planetCrudService.findAll();
        int expected = planetEntityExpected.size() - 1;
        planetCrudService.delete(planetID);
        List<PlanetEntity> planetEntityActual = planetCrudService.findAll();
        int actual = planetEntityActual.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void planetCrudServiceInsertExpectedException() {
        PlanetEntity planetEntity = new PlanetEntity();
        planetEntity.setId("planet777");
        planetEntity.setName("Planet 777");
        Assertions.assertThrows(Exception.class, () -> planetCrudService.create(planetEntity));
    }

    @Test
    void planetCrudServiceUpdate() {
        String expected = "Venus " + LocalDateTime.now();
        PlanetDto planetDto = PlanetDto.of("VENUS", expected);
        PlanetEntity planetEntity = PlanetDtoMapper.instance().map(planetDto);
        planetCrudService.update(planetEntity);
        String actual = planetCrudService.getById("VENUS").getName();
        Assertions.assertEquals(expected, actual);
    }
}