package com.chemaxon.homework.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by szfilep on 15. 09. 04..
 * <p>
 * Molecule entity
 */
@Entity
public class Molecule {

    /**
     * External ID, provided by the user. Required, max 20 characters, unique.
     */
    @Id
    @NotNull
    @Size(min=1, max = 20)
    @Column(unique = true, length = 20, nullable = false)
    private String externalId;

    /**
     * The moluecule structure’s text representation. Required, max 1000 characters.
     */
    @NotNull
    @Size(min=1, max = 1000)
    @Column(length = 1000, nullable = false)
    private String textRepresentation;

    /**
     * 0-n number of metadata which are key,value text pairs. Max 100 characters.
     */
    @OneToMany(mappedBy = "molecule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MetadataEntry> metadata;

    public Molecule() {
    }

    public Molecule(String externalId, String textRepresentation, List<MetadataEntry> metadata) {
        this.externalId = externalId;
        this.textRepresentation = textRepresentation;
        this.metadata = metadata;

    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTextRepresentation() {
        return textRepresentation;
    }

    public void setTextRepresentation(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    public List<MetadataEntry> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataEntry> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Molecule{" +
                "externalId='" + externalId + '\'' +
                ", textRepresentation='" + textRepresentation + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
