package com.chemaxon.homework.service;

import com.chemaxon.homework.domain.MetadataEntry;
import com.chemaxon.homework.domain.Molecule;
import com.chemaxon.homework.repository.MoleculeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by szfilep on 15. 09. 06..
 *
 * Tests for MoleculeService
 */
@RunWith(MockitoJUnitRunner.class)
public class MoleculeServiceTests {

    @Mock
    private MoleculeRepository moleculeRepository;

    @Test
    public void shouldConnectMetadataToItsMoleculeObject() {
        MoleculeService moleculeService = new MoleculeService();
        moleculeService.setMoleculeRepository(moleculeRepository);

        Molecule molecule = new Molecule(null, null, Arrays.asList(new MetadataEntry("k1", "v1"), new MetadataEntry("k2", "v2")));
        when(moleculeRepository.save(any(Molecule.class))).thenReturn(molecule);

        Molecule returnedMolecule = moleculeService.saveNew(molecule);

        verify(moleculeRepository, times(1)).save(molecule);
        assertEquals("1. Metadata entry's molecule property should be point to the parent molecule instance.", returnedMolecule.getMetadata().get(0).getMolecule(), molecule);
        assertEquals("2. Metadata entry's molecule property should be point to the parent molecule instance.", returnedMolecule.getMetadata().get(1).getMolecule(), molecule);
    }
}
