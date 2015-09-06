package com.chemaxon.homework.controller;

import com.chemaxon.homework.domain.Molecule;
import com.chemaxon.homework.service.MoleculeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by szfilep on 15. 09. 05..
 *
 * * Tests for MoleculeController
 */
@RunWith(MockitoJUnitRunner.class)
public class MoleculeControllerTests {

    @Mock
    private MoleculeService moleculeService;

    private MoleculeController moleculeController;

    @Before
    public void setUp() {
        moleculeController = new MoleculeController();
        moleculeController.setMoleculeService(moleculeService);
    }

    /**
     * Verifies that the moleculeService saveNew() method is called and the saved one is used in the response.
     */
    @Test
    public void shouldCreateMoleculeWhenPosting() {
        final Molecule savedMolecule = new Molecule("externalId", null, null);
        when(moleculeService.saveNew(any(Molecule.class))).thenReturn(savedMolecule);

        final Molecule molecule = new Molecule();
        ResponseEntity<String> responseEntity = moleculeController.post(molecule);

        verify(moleculeService, times(1)).saveNew(molecule);
        assertEquals("Returned molecule should come from the service ", savedMolecule.getExternalId(), extrackExternalIdFromHttpHeaders(responseEntity));
    }

    /**
     * Verifies that the moleculeService findOne() method is called and that is used in the response.
     */
    @Test
    public void shouldQueryMoleculeWhenGetting() {
        String externalId = "externalId";
        Molecule savedMolecule = new Molecule();
        when(moleculeService.findOne(externalId)).thenReturn(savedMolecule);

        Molecule returnedMolecule = moleculeController.get(externalId);

        verify(moleculeService, times(1)).findOne(externalId);
        assertEquals("Returned molecule should com from the service ", savedMolecule, returnedMolecule);

    }

    private String extrackExternalIdFromHttpHeaders(ResponseEntity<String> responseEntity) {
        String query = responseEntity.getHeaders().getLocation().toString();
        return query.substring(MoleculeController.LOCATION_PREFIX.length());
    }

}
