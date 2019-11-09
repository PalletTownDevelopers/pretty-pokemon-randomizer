package com.likeageek.randomizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AsmFileParserTest {

    private AsmFileParser asmFileParser = new AsmFileParser();

    @Test
    public void shouldReplaceAValueInAsmLine() {
        String line = asmFileParser.editLine("an, asm, line", "asm-replaced", 2);
        assertThat(line).isEqualTo("an, asm-replaced, line");
    }


    //db $FF,45,RHYHORN,42,DUGTRIO,44,NIDOQUEEN,45,NIDOKING,50,RHYDON,0

}