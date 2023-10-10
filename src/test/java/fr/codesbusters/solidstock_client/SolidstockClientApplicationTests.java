package fr.codesbusters.solidstock_client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SolidstockClientApplicationTests {

    @Test
    public void testOneOne() {
        assertEquals(1+1, 2);
    }

}
