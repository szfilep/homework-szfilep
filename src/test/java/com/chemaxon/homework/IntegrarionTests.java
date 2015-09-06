package com.chemaxon.homework;

import com.chemaxon.homework.domain.MetadataEntry;
import com.chemaxon.homework.domain.Molecule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by szfilep on 15. 09. 06..
 * <p>
 *     Integration tests that runs the webcontainer and tests call the rest api.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MoleculeApplication.class)
@WebIntegrationTest("server.port:" + IntegrarionTests.PORT)
public class IntegrarionTests {
    public static final int PORT = 19000;
    public static final String BASE_URL = "http://localhost:" + PORT;
    public static final String URL_TO_POST = BASE_URL + "/molecule";

    private static final Logger LOG = LoggerFactory.getLogger(MoleculeApplication.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPostAndGetMolecule() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule("testMoleculeId", "{molecule structure}", Arrays.asList(new MetadataEntry("m1", "v1"), new MetadataEntry("m2", "v2")));
        LOG.debug("Molecule to create: {}", objectMapper.writeValueAsString(moleculeToPost));
        URI location = restTemplate.postForLocation(URL_TO_POST, moleculeToPost);
        LOG.debug("Molecule successfully created. Location: {}", location);

        ResponseEntity<Molecule> moleculeResponseEntity = restTemplate.getForEntity(BASE_URL + location.toString(), Molecule.class);
        Molecule returnedMolecule = moleculeResponseEntity.getBody();
        LOG.debug("Molecule successfully retrived: {}", returnedMolecule);

        assertEquals("Should return the same representation", objectMapper.writeValueAsString(returnedMolecule), objectMapper.writeValueAsString(moleculeToPost));
    }

    @Test
    public void testPostAndGetMoleculeWithoutMetadata() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule("testMoleculeId2", "{molecule structure}", Collections.<MetadataEntry>emptyList());
        LOG.debug("Molecule to create: {}", objectMapper.writeValueAsString(moleculeToPost));
        URI location = restTemplate.postForLocation(URL_TO_POST, moleculeToPost);
        LOG.debug("Molecule successfully created. Location: {}", location);

        ResponseEntity<Molecule> moleculeResponseEntity = restTemplate.getForEntity(BASE_URL + location.toString(), Molecule.class);
        Molecule returnedMolecule = moleculeResponseEntity.getBody();
        LOG.debug("Molecule successfully retrived: {}", returnedMolecule);

        assertEquals("Should return the same representation", objectMapper.writeValueAsString(returnedMolecule), objectMapper.writeValueAsString(moleculeToPost));
    }

    @Test(expected = HttpClientErrorException.class)
    public void testAlreadyExistsValidation() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule("testMoleculeId3", "{molecule structure}", null);

        restTemplate.postForObject(URL_TO_POST, moleculeToPost, ResponseEntity.class);
        //second call:
        restTemplate.postForObject(URL_TO_POST, moleculeToPost, ResponseEntity.class);
    }


    @Test(expected = HttpClientErrorException.class)
    public void testMissingExternalIdValidation() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule(null, "{molecule structure}", Arrays.asList(new MetadataEntry("m1", "v1"), new MetadataEntry("m2", "v2")));
        restTemplate.postForObject(URL_TO_POST, moleculeToPost, ResponseEntity.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testMissingTextStrcutureValidation() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule("testMoleculeId", null, Arrays.asList(new MetadataEntry("m1", "v1"), new MetadataEntry("m2", "v2")));
        restTemplate.postForObject(URL_TO_POST, moleculeToPost, ResponseEntity.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testMissingMetadataKeyValidation() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule("testMoleculeId", "{molecule structure}", Arrays.asList(new MetadataEntry(null, "v1"), new MetadataEntry("m2", "v2")));
        restTemplate.postForObject(URL_TO_POST, moleculeToPost, ResponseEntity.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testTooLongExternalIdKeyValidation() throws JsonProcessingException {
        Molecule moleculeToPost = new Molecule("tooLongTestMoleculeId123456789", "{molecule structure}", null);
        restTemplate.postForObject(URL_TO_POST, moleculeToPost, ResponseEntity.class);
    }

}
