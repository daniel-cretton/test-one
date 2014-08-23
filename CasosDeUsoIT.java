package ar.org.example.app.aceptacion;

import ar.org.example.app.aceptacion.beans.HacerValeBean;
import ar.org.example.app.aceptacion.beans.HacerValeConTurnoBean;
import ar.org.example.util.DataFactory;
import ar.org.example.util.WebApp;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class CasosDeUsoIT {

    private static WebApp cajeroApp;

    @BeforeClass
    public static void setUp() throws Exception {
        cajeroApp = new WebApp(new ChromeDriver());
        cajeroApp.getBrowserDriver().start();
    }

    @Test
    public void testHacerValeSinTurno_PorId_PorIds() throws Exception {
        System.out.println("*** testHacerValeSinTurno_PorId_PorIds");
        HacerValeBean hacerVale = DataFactory.getHacerValeSinTurnoFlujoBasicoBean();
        cajeroApp.hacerLogin(hacerVale.getLoginBean());
        cajeroApp.validarPaginaPrincipal(hacerVale.getLoginBean().getInfo());
        cajeroApp.navegarPaginaHacerValeSinTurno();
        cajeroApp.validarPaginaHacerValeSinTurno();
        cajeroApp.identificarAfiliadoPorId(hacerVale.getIdentificarAfiliadoBean());
        cajeroApp.validarInfoAfiliado(hacerVale.getIdentificarAfiliadoBean().getInfo());
        cajeroApp.identificarEfectorPorIds(hacerVale.getIdentificarEfectorBean());
        cajeroApp.validarInfoEfector(hacerVale.getIdentificarEfectorBean().getInfo());
        // TODO: Validar los mensajes de modalidad operativa
        cajeroApp.confirmarCreacionVale();
        cajeroApp.validarValeCreado(hacerVale.getValeInfo());
    }

    @Test
    public void testHacerValeSinTurno_PorId_PorDescripciones() throws Exception {
        System.out.println("*** testHacerValeSinTurno_PorId_PorDescripciones");
        HacerValeBean hacerVale = DataFactory.getHacerValeSinTurnoPorIdPorDescripciones();
        cajeroApp.hacerLogin(hacerVale.getLoginBean());
        cajeroApp.validarPaginaPrincipal(hacerVale.getLoginBean().getInfo());
        cajeroApp.navegarPaginaHacerValeSinTurno();
        cajeroApp.validarPaginaHacerValeSinTurno();
        cajeroApp.identificarAfiliadoPorId(hacerVale.getIdentificarAfiliadoBean());
        cajeroApp.validarInfoAfiliado(hacerVale.getIdentificarAfiliadoBean().getInfo());
        cajeroApp.identificarEfectorPorDescripciones(hacerVale.getIdentificarEfectorBean());
        cajeroApp.validarInfoEfector(hacerVale.getIdentificarEfectorBean().getInfo());
        // TODO: Validar los mensajes de modalidad operativa
        cajeroApp.confirmarCreacionVale();
        cajeroApp.validarValeCreado(hacerVale.getValeInfo());
    }

    @Test
    public void testHacerValeSinTurno_PorDocumento_PorIds() throws Exception {
        System.out.println("*** testHacerValeSinTurno_PorDocumento_PorIds");
        HacerValeBean hacerVale = DataFactory.getHacerValeSinTurnoPorDocumentoPorIdsBean();
        cajeroApp.hacerLogin(hacerVale.getLoginBean());
        cajeroApp.validarPaginaPrincipal(hacerVale.getLoginBean().getInfo());
        cajeroApp.navegarPaginaHacerValeSinTurno();
        cajeroApp.validarPaginaHacerValeSinTurno();
        cajeroApp.identificarAfiliadoPorDocumento(hacerVale.getIdentificarAfiliadoBean());
        cajeroApp.validarInfoAfiliado(hacerVale.getIdentificarAfiliadoBean().getInfo());
        cajeroApp.identificarEfectorPorIds(hacerVale.getIdentificarEfectorBean());
        cajeroApp.validarInfoEfector(hacerVale.getIdentificarEfectorBean().getInfo());
        // TODO: Validar los mensajes de modalidad operativa
        cajeroApp.confirmarCreacionVale();
        cajeroApp.validarValeCreado(hacerVale.getValeInfo());
    }

    @Test
    public void testHacerValeSinTurno_PorDatosMinimos_PorIds() throws Exception {
        System.out.println("*** testHacerValeSinTurno_PorDatosMinimos_PorIds");
        HacerValeBean hacerVale = DataFactory.getHacerValeSinTurnoPorDatosMinimosPorIdsBean();
        cajeroApp.hacerLogin(hacerVale.getLoginBean());
        cajeroApp.validarPaginaPrincipal(hacerVale.getLoginBean().getInfo());
        cajeroApp.navegarPaginaHacerValeSinTurno();
        cajeroApp.validarPaginaHacerValeSinTurno();
        cajeroApp.identificarAfiliadoPorDatosMinimos(hacerVale.getIdentificarAfiliadoBean());
        cajeroApp.validarInfoAfiliado(hacerVale.getIdentificarAfiliadoBean().getInfo());
        cajeroApp.identificarEfectorPorIds(hacerVale.getIdentificarEfectorBean());
        cajeroApp.validarInfoEfector(hacerVale.getIdentificarEfectorBean().getInfo());
        // TODO: Validar los mensajes de modalidad operativa
        cajeroApp.confirmarCreacionVale();
        cajeroApp.validarValeCreado(hacerVale.getValeInfo());
    }

    @Test
    public void testHacerValeConTurno_PorFechaHoraIdPaciente() throws Exception {
        System.out.println("*** testHacerValeConTurno_PorFechaHoraIdPaciente");
        HacerValeConTurnoBean hacerVale = DataFactory.getHacerValeConTurnoBean();
        cajeroApp.hacerLogin(hacerVale.getLoginBean());
        cajeroApp.validarPaginaPrincipal(hacerVale.getLoginBean().getInfo());
        cajeroApp.navegarPaginaHacerValeConTurno();
        cajeroApp.validarPaginaSeleccionarTurno();
        cajeroApp.buscarTurnosPorFechaHoraPaciente(hacerVale.getSeleccionarTurnoBean());
        cajeroApp.validarListaTurnosReservados(hacerVale.getSeleccionarTurnoBean().getInfoList());
        cajeroApp.seleccionarTurnoReservado();
        cajeroApp.validarPaginaHacerValeConTurno(hacerVale);
        cajeroApp.confirmarCreacionVale();
        cajeroApp.validarValeConTurnoCreado(hacerVale.getValeInfo());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        cajeroApp.getBrowserDriver().quit();
    }
}
