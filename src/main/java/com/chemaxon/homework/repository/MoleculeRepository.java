package com.chemaxon.homework.repository;

import com.chemaxon.homework.domain.Molecule;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by szfilep on 15. 09. 04..
 * <p>
 * Molecule repository thats is implemented by the framework.
 */
public interface MoleculeRepository extends CrudRepository<Molecule, String> {
}
