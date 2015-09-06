package com.chemaxon.homework.controller;

import com.chemaxon.homework.domain.Molecule;
import com.chemaxon.homework.service.MoleculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

/**
 * Created by szfilep on 15. 09. 04..
 * <p>
 * Controller that implements molecule rest api.
 */
@RestController
@RequestMapping("/molecule")
public class MoleculeController {

    public static final String LOCATION_PREFIX = "/molecule?externalId=";

    @Autowired
    MoleculeService moleculeService;

    /**
     * Query for a molecule represenatation.
     *
     * @param externalId the molecule's externalId
     * @return the molecules object
     */
    @RequestMapping(method = RequestMethod.GET)
    public Molecule get(@RequestParam final String externalId) {

        Molecule molecule = moleculeService.findOne(externalId);
        if (molecule == null) {
            throw new EntityNotFoundException("Molecule not found for the given externalId.");
        }
        return molecule;
    }

    /**
     * Creates a new molecule representation.
     *
     * @param molecule the molecule object to be stored
     * @return 201 http status code with location header
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> post(@RequestBody @Valid final Molecule molecule) {
        Molecule savedMolecule = moleculeService.saveNew(molecule);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, LOCATION_PREFIX + savedMolecule.getExternalId());
        ResponseEntity<String> responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        return responseEntity;
    }

    public void setMoleculeService(MoleculeService moleculeService) {
        this.moleculeService = moleculeService;
    }
}
