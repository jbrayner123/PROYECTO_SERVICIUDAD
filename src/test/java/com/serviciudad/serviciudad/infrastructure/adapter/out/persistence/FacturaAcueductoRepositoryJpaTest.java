package com.serviciudad.serviciudad.infrastructure.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FacturaAcueductoRepositoryJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FacturaAcueductoRepositoryJpa repository;

    @Test
    void testFindByIdCliente() {
        FacturaAcueductoEntity entity = new FacturaAcueductoEntity("123", "2023-10", 20, new BigDecimal("30000"));
        entityManager.persist(entity);
        entityManager.flush();

        List<FacturaAcueductoEntity> found = repository.findByIdCliente("123");

        assertEquals(1, found.size());
        assertEquals("123", found.get(0).getIdCliente());
    }
}
