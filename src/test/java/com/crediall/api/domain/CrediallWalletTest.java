package com.crediall.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.crediall.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrediallWalletTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrediallWallet.class);
        CrediallWallet crediallWallet1 = new CrediallWallet();
        crediallWallet1.setId(1L);
        CrediallWallet crediallWallet2 = new CrediallWallet();
        crediallWallet2.setId(crediallWallet1.getId());
        assertThat(crediallWallet1).isEqualTo(crediallWallet2);
        crediallWallet2.setId(2L);
        assertThat(crediallWallet1).isNotEqualTo(crediallWallet2);
        crediallWallet1.setId(null);
        assertThat(crediallWallet1).isNotEqualTo(crediallWallet2);
    }
}
