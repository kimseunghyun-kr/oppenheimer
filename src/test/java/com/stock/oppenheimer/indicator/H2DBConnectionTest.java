package com.stock.oppenheimer.indicator;

import com.stock.oppenheimer.indicators.VolumeProfilePythonAdapter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@Slf4j
public class H2DBConnectionTest {
    @Autowired
    VolumeProfilePythonAdapter volumeProfilePythonAdapter;

    @Test
    void H2DBConnectionTest(){
        volumeProfilePythonAdapter.runPythonScript("H2", "0","삼성전자");
    }


}
