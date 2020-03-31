package com.likeageek.randomizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AsmFileParserTest {

    private AsmFileParser asmFileParser = new AsmFileParser();

    @Test
    void shouldReplaceAValueInAsmLine() {
        String line = asmFileParser.editLine("an, asm, line", "asm-replaced", 2);
        assertThat(line).isEqualTo("an, asm-replaced, line");
    }

    @Test
    void shouldReplaceAValueInAsmLine_whenDbExists() {
        String line = asmFileParser.editLine("db 42, asm, line", "007", 1);
        assertThat(line).isEqualTo("db 007, asm, line");
    }
}