package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class LogTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogType.class);
        LogType logType1 = new LogType();
        logType1.setId(1L);
        LogType logType2 = new LogType();
        logType2.setId(logType1.getId());
        assertThat(logType1).isEqualTo(logType2);
        logType2.setId(2L);
        assertThat(logType1).isNotEqualTo(logType2);
        logType1.setId(null);
        assertThat(logType1).isNotEqualTo(logType2);
    }
}
