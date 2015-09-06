package com.chemaxon.homework;

import com.chemaxon.homework.domain.MetadataEntry;
import com.chemaxon.homework.domain.Molecule;
import com.chemaxon.homework.service.MoleculeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * Created by szfilep on 15. 09. 05..
 * <p>
 * The main application class, that creates a couple of init data.
 */
@SpringBootApplication
public class MoleculeApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MoleculeApplication.class);

    @Autowired
    private MoleculeService moleculeService;

    public static void main(String[] args) {
        SpringApplication.run(MoleculeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Create init data
        try {
            moleculeService.saveNew(new Molecule("1", "{atoms:[C,O,C,C]}", Arrays.asList(new MetadataEntry("metadata1", "value1"), new MetadataEntry("m1", "v1"))));
            moleculeService.saveNew(new Molecule("2", "{atoms:[H,O,C,C]}", Arrays.asList(new MetadataEntry("metadata2", "value2"), new MetadataEntry("m2", "v2"))));
            moleculeService.saveNew(new Molecule("3", "{atoms:[R,O,C,C]}", Arrays.asList(new MetadataEntry("metadata3", "value3"), new MetadataEntry("m3", "v3"))));
            LOG.info("Initial data has been created.");
        } catch (IllegalArgumentException e) {
            LOG.info("Initial data already has been created.");
        }

    }
}
