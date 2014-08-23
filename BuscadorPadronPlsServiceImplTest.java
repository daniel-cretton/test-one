package ar.org.example.service.impl;

import ar.org.example.dto.PadronPlsDto;
import ar.org.example.service.BuscadorPadronPlsService;
import ar.org.example.testutils.DataFactory;
import static ar.org.example.testutils.Matchers.mismoPadronPlsDto;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel.cretton
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BuscadorPadronPlsServiceImplTest {

    @Autowired
    private BuscadorPadronPlsService padronPlsService;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of buscarAfiliadoPorNumero method, of class BuscadorPadronPlsService.
     */
    @Test
    @Transactional
    public void testBuscarAfiliadoPorNumero_Existe_Afiliado() {
        System.out.println("testBuscarAfiliadoPorNumero_Existe_Afiliado");
        String numeroAfiliado = DataFactory.PADRON_UNO.getIdAfiliado();
        PadronPlsDto result = padronPlsService.buscarAfiliadoPorNumero(numeroAfiliado);
        assertThat(result, mismoPadronPlsDto(DataFactory.PADRON_UNO));
    }

    @Test
    @Transactional
    public void testBuscarAfiliadoPorNumero_NoExiste_Nulo() {
        System.out.println("testBuscarAfiliadoPorNumero_NoExiste_Nulo");
        String numeroAfiliadoInexistente = "9999999";
        PadronPlsDto result = padronPlsService.buscarAfiliadoPorNumero(numeroAfiliadoInexistente);
        assertNull(result);
    }

    /**
     * Test of buscarAfiliadoPorIdPaciente method, of class BuscadorPadronPlsService.
     */
    @Test
    @Transactional
    public void testBuscarAfiliadoPorIdPaciente_Existe_Afiliado() {
        System.out.println("testBuscarAfiliadoPorIdPaciente_Existe_Afiliado");
        String idPaciente = DataFactory.PADRON_UNO.getIdPaciente().toString();
        PadronPlsDto afiliadoPls = padronPlsService.buscarAfiliadoPorIdPaciente(idPaciente);
        assertThat(afiliadoPls, mismoPadronPlsDto(DataFactory.PADRON_UNO));
    }
}
