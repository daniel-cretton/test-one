package ar.org.example.service.impl;

import ar.org.example.exception.PacienteParser1Exception;
import ar.org.example.service.AreaJerarquicaParserService;
import ar.org.example.dto.AreaJerarquicaDto;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author daniel
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:spring/applicationContext.xml"
})
public class AreaJerarquicaParserServiceImplTest {

    @Autowired
    AreaJerarquicaParserService parserService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
     * Test of getAreasJerarquicasFromStringXml method, of class AreaJerarquicaParserService.
     */
    @Test
    public void testgetAreasJerarquicasFromStringXml_Ok_ListaAreas() {
        System.out.println("testgetAreasJerarquicasFromStringXml_Ok_ListaAreas");
        String areaXml = xmlAreasJerarquicaBienFormado();
        List<AreaJerarquicaDto> result = parserService.getAreasJerarquicasFromStringXml(areaXml);
        assertNotNull("Document es nulo", result);
    }

    @Test
    public void testGetAreasJerarquicasFromStringXml_xmlNotOk() {
        System.out.println("testGetAreasJerarquicasFromStringXml_xmlNotOk");
        String areaXml = xmlAreasJerarquicaMalFormado();
        expectedException.expect(PacienteParser1Exception.class);
        expectedException.expectMessage("debe finalizar por la etiqueta final");
        parserService.getAreasJerarquicasFromStringXml(areaXml);
    }

    @Test
    public void testGetAreasJerarquicaFromStringXml__xmlVacio() {
        System.out.println("testGetAreasJerarquicaFromStringXml__xmlVacio");
        String areaXml = "";
        expectedException.expect(PacienteParser1Exception.class);
        expectedException.expectMessage("Final de archivo prematuro");
        parserService.getAreasJerarquicasFromStringXml(areaXml);
    }

    private String xmlAreasJerarquicaMalFormado() {
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        builder.append("<RESULTADO>");
        builder.append("<REGISTRO>ESTO NO ES XML");
        builder.append("</RESULTADO>");
        return builder.toString();
    }

    private String xmlAreasJerarquicaBienFormado() {
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        builder.append("<RESULTADO>");
        builder.append("<ID_AREA>365</ID_AREA>");
        builder.append("<DESCRIPCION_RESUMIDA>LABORATORIO CENTRAL</DESCRIPCION_RESUMIDA>");
        builder.append("<DESCRIPCION>SERVICIO DE LABORATORIO CENTRAL</DESCRIPCION>");
        builder.append("</RESULTADO>");
        return builder.toString();
    }
}
