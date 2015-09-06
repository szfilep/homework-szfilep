package com.chemaxon.homework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by szfilep on 15. 09. 05..
 * <p>
 * Metadata entity for molecules.
 */
@Entity
public class MetadataEntry{

    /**
     * Technical id. Used to able to store multiple metadata with the same metadataKey.
     */
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long techId;

    /**
     * Key part of the metada. Max length is 100.
     */
    @NotNull
    @Size(min=1, max = 100)
    @Column(length = 100, nullable = false)
    private String metadataKey;

    /**
     * Value part of the metada. Max length is 100.
     */
    @Column(length = 100)
    private String metadataValue;

    @ManyToOne
    @JsonIgnore
    private Molecule molecule;

    public MetadataEntry() {
    }

    public MetadataEntry(String metadataKey, String metadataValue) {
        this.metadataKey = metadataKey;
        this.metadataValue = metadataValue;
    }

    public String getMetadataKey() {
        return metadataKey;
    }

    public void setMetadataKey(String metadataKey) {
        this.metadataKey = metadataKey;
    }

    public String getMetadataValue() {
        return metadataValue;
    }

    public void setMetadataValue(String metadataValue) {
        this.metadataValue = metadataValue;
    }

    public Molecule getMolecule() {
        return molecule;
    }

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
    }

    public Long getTechId() {
        return techId;
    }

    public void setTechId(Long techId) {
        this.techId = techId;
    }

    @Override
    public String toString() {
        return "MetadataEntry{" +
                "metadataKey='" + metadataKey + '\'' +
                ", metadataValue='" + metadataValue + '\'' +
                '}';
    }
}
