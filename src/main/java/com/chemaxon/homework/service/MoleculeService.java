package com.chemaxon.homework.service;

import com.chemaxon.homework.domain.MetadataEntry;
import com.chemaxon.homework.domain.Molecule;
import com.chemaxon.homework.repository.MoleculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by szfilep on 15. 09. 05..
 */
@Component
public class MoleculeService {

    @Autowired
    MoleculeRepository moleculeRepository;

    /**
     * Sets the metadata entires' molecule reference and saves them. Assumes that the metadata relationship is set to CascadeType.ALL.
     *
     * @param molecule the molecule object
     * @return the persisted object
     */
    public Molecule saveNew(Molecule molecule) {
        Molecule foundMolecule = moleculeRepository.findOne(molecule.getExternalId());
        if (null != foundMolecule) {
            throw new IllegalArgumentException("Molecule already exists in db with the given externalId.");
        }

        if (molecule.getMetadata() != null) {
            for (MetadataEntry entry : molecule.getMetadata()) {
                entry.setMolecule(molecule);
            }
        }

        return moleculeRepository.save(molecule);
    }

    /**
     * Returns the object for the externalId from the reporsitory.
     *
     * @param externalId the string representation of the externalId
     * @return the molecule object if it was found in the repository, or null if none found
     */
    public Molecule findOne(String externalId) {
        return moleculeRepository.findOne(externalId);
    }

    public void setMoleculeRepository(MoleculeRepository moleculeRepository) {
        this.moleculeRepository = moleculeRepository;
    }
}
