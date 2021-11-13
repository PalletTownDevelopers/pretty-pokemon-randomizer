package com.likeageek.randomizer.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.quarkiverse.hibernate.types.json.JsonBinaryType;
import io.quarkiverse.hibernate.types.json.JsonType;
import io.quarkiverse.hibernate.types.json.JsonTypes;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity(name = "ppr_rom")
@TypeDef(name = JsonTypes.JSON, typeClass = JsonType.class)
@TypeDef(name = JsonTypes.JSON_BIN, typeClass = JsonBinaryType.class)
public class Rom extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "pprRomIdSequence",
            sequenceName = "ppr_rom_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ppr_rom_id_seq")
    public Integer id;
    private Long seed;

    @Type(type = JsonTypes.JSON_BIN)
    @Column(name = "parameters", columnDefinition = JsonTypes.JSON_BIN)
    private RomInformation parameters;

    public Rom(Long seed, RomInformation romInformation) {
        this.seed = seed;
        this.parameters = romInformation;
    }

    public Rom() {

    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Long getSeed() {
        return seed;
    }
}
