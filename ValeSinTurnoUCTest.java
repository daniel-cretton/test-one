package ar.org.example.app.web.controller.uc;

import ar.org.example.app.web.controller.beans.AfiliadoBean;
import ar.org.example.app.web.controller.beans.EfectorBean;
import ar.org.example.web.utiles.DataFactory;
import ar.org.example.web.utiles.MockSetup;
import ar.org.example.dto.AreaJerarquicaDto;
import ar.org.example.dto.Medico;
import ar.org.example.dto.EfectorDto;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test de caso de uso Hacer vale con turno
 *
 * @author daniel
 *
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/webmvc-config.xml",
    "classpath:spring/applicationContext.xml", "classpath:spring/applicationContext-security.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ValeSinTurnoUCTest {

    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpServletRequest request;
    MockMvc mvc;
    @Autowired
    MockHttpSession session;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Armar acá lo que el web.xml haría sobre el wac
     *
     */
    @Before
    public void setUp() {
        this.mvc = MockSetup.buildMockMvc(wac);
        MockSetup.addAuthenticatedCajeroUserToSession(session);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test de flujo básico.
     * <pre>
     * {@code
     * 1- El cajero selecciona un turno reservado (ver caso de uso CU01)
     * 2- El sistema le muestra al cajero los datos para realizar el vale, inicializados por defecto
     * 3- El cajero identifica un paciente afiliado a PLS con cobertura vigente por id de paciente (ver caso de uso CUNN)
     * 4- El cajero identifica área jerárquica efectora y médico efector
     * 5- El sistema valida el efector
     * 7- El sistema valida las precondiciones necesarias para la creación del vale con turno y muestra la modalidad operativa de
     * la consulta
     * 8- El cajero confirma la creación del vale
     * 9- El sistema crea un vale ambulatorio externo con la consulta para el efector del turno, confirma el turno y crea una factura
     * en la cuenta corriente del afiliado si es necesario
     * 10- El sistema muestra el número de vale creado
     * }</pre>
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void testHacerValeSinTurnoFlujoBasico() throws Exception {
        System.out.println("testHacerValeSinTurnoFlujoBasico");
        AfiliadoBean afiliado = DataFactory.getTestAfiliado();
        Medico medico = DataFactory.getTestMedico();
        EfectorDto efectorDto = DataFactory.getEfectorDtoUno();
        EfectorBean efector = DataFactory.getEfectorUno();
        String idMedico = new Long(efector.getIdMedico()).toString();
        AreaJerarquicaDto area = DataFactory.getAreaJerarquicaUno();
        String idArea = area.getIdArea().toString();
        String idPaciente = Integer.toString(afiliado.getIdPaciente());
        // 1- El cajero inicia la creación del vale sin turno 
        // 2- El sistema le muestra al cajero los datos para realizar el vale, inicializados por defecto   
        this.mvc.perform(post("/valeSinTurno").session(session)
                .param("form", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("valeSinTurno/crear"))
                .andExpect(model().attributeExists("porId", "documento", "cobertura", "efector", "solicitante", "datosMinimos", "tiposDoc",
                "sexos", "foto", "valeSinTurno", "tieneTurno", "idTurno", "afiliadoJson", "observaciones"));
        // 3- El cajero identifica un paciente afiliado a PLS con cobertura vigente por id de paciente (ver caso de uso CUNN)
        this.mvc.perform(post("/identificarAfiliadoPLS/buscarPorId")
                .session(session)
                .param("idAfiliado", "0")
                .param("idPaciente", idPaciente))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.apellido").value(afiliado.getApellido()))
                .andExpect(jsonPath("$.data.nombreYApellido").value(afiliado.getNombreYApellido()))
                .andExpect(jsonPath("$.data.tipoDocumento").value(afiliado.getTipoDocumento()))
                .andExpect(jsonPath("$.data.numeroDocumento").value(afiliado.getNumeroDocumento()))
                .andExpect(jsonPath("$.data.numeroAfiliado").value(afiliado.getNumeroAfiliado()))
                .andExpect(jsonPath("$.data.idPlan").value(afiliado.getIdPlan()))
                .andExpect(jsonPath("$.data.idProgramaMedico").value(afiliado.getIdProgramaMedico()))
                .andExpect(jsonPath("$.data.programaMedicoDesc").value(afiliado.getProgramaMedicoDesc()));
        // 4- El cajero identifica área jerárquica efectora y médico efector por ids
        this.mvc.perform(post("/efector/buscarArea")
                .session(session)
                .param("id", idArea))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.descripcion").value(area.getDescripcion()))
                .andExpect(jsonPath("$.data.id").value(area.getId()));
        this.mvc.perform(post("/efector/buscarMedico")
                .session(session)
                .param("id", idMedico))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.nombre").value(medico.getNombre()))
                .andExpect(jsonPath("$.data.idPersonaFederada").value(medico.getIdPersonaFederada()))
                .andExpect(jsonPath("$.data.idPersonaInstitucional").value(medico.getIdPersonaInstitucional()))
                .andExpect(jsonPath("$.data.tipoMatricula").value(medico.getTipoMatricula()))
                .andExpect(jsonPath("$.data.numeroMatricula").value(medico.getNumeroMatricula()));
        // 5- El sistema valida la relación entre área y médico efector
        this.mvc.perform(post("/efector/validarEfector")
                .session(session)
                .param("idAreaJerarquica", efector.getIdAreaJerarquica().toString())
                .param("idMedico", efector.getIdMedico().toString())
                .param("areaJerarquica", "")
                .param("medico", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.idAreaJerarquica").value(efectorDto.getIdAreaJerarquica().intValue()))
                .andExpect(jsonPath("$.data.idMedico").value(efectorDto.getIdMedico().intValue()))
                .andExpect(jsonPath("$.data.areaJerarquica").value(efectorDto.getAreaJerarquica()))
                .andExpect(jsonPath("$.data.medico").value(efectorDto.getMedico()));
        // 6- el sistema valida las precondiciones necesarias para la creación del vale con turno y muestra la modalidad operativa de la consulta
        this.mvc.perform(post("/valeSinTurno/validarPrecondicion")
                .session(session)
                .param("idPaciente", idPaciente)
                .param("apellido", afiliado.getApellido())
                .param("nombre", afiliado.getNombre())
                .param("otrosNombres", afiliado.getOtrosNombres())
                .param("otrosApellidos", afiliado.getOtrosApellidos())
                .param("tipoDocumento", "")
                .param("numeroDocumento", "3586402")
                .param("idPacienteAdministrativo", idPaciente)
                .param("idPacienteAsistencial", idPaciente)
                .param("sexo", "M")
                .param("estado", "A")
                .param("fechaNacimiento", "07/02/1984")
                .param("idProgramaMedico", "2")
                .param("idPlan", "GEN")
                .param("numeroAfiliado", afiliado.getNumeroAfiliado())
                .param("morosidad", "0")
                .param("idAreaJerarquicaEfector", efectorDto.getIdAreaJerarquica().toString())
                .param("idMedicoEfector", efectorDto.getIdMedico().toString())
                .param("idAreaJerarquicaSolic", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists());
    }
}
