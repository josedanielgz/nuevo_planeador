package com.planeador.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.planeador.servicio.DocenteServicio;
import com.planeador.servicio.InstrumentoEvaluacionServicio;
import com.planeador.servicio.MateriaServicio;
import com.planeador.servicio.MicrocurriculoServicio;
import com.planeador.servicio.PlaneadorServicio;
import com.planeador.modelo.Docente;
import com.planeador.modelo.InstrumentoEvaluacion;
import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;
import com.planeador.modelo.Planeador;

//Este controlador administra todas las rutas del aplicativo que se van como /docente/{lo que sea}
@Controller
@RequestMapping("/docente")
public class DocenteControlador {

	@Autowired
	private DocenteServicio docenteServicio;
	@Autowired
	private PlaneadorServicio planeadorServicio;
	@Autowired
	private MicrocurriculoServicio microcurriculoServicio;
	@Autowired
	private InstrumentoEvaluacionServicio instrumentoServicio;

//	@Autowired
//	private MateriaServicio MateriaServicio;

//	Esto vendría a sustituir lo que estaba en ProductoController.java del ejercicio de refencia, 
//	básicamente para recuperar los datos del docente. y mostrarlos en la topbar a lo largo de la
//	sesión

	@ModelAttribute("docente")
	public Docente getDocenteActual(Model model, HttpServletRequest request) {
		Docente docente = (Docente) model.getAttribute("docente");
		if (docente == null || docente.isEmpty()) {
			int docente_id;
			try {
				docente_id = (int) request.getSession().getAttribute("docente_id");
			} catch (NullPointerException e) {
				docente_id = 0;
			}
			if (docente_id == 0) {
				docente = new Docente();
			} else {
				docente = this.docenteServicio.get(docente_id);
			}
			model.addAttribute("docente", docente);
		}
		return docente;
	}

	@GetMapping("")
	public String rutaPorDefectoDocente(HttpServletRequest request, HttpSession session, Model model) {
		if (request.getSession().getAttribute("docente_id") != null) {
			return "redirect:/docente/main";
		} else
			return "login_docente";
	}

	@PostMapping("/login")
	public String validarInicioSesionDocente(RedirectAttributes att, @RequestParam String email,
			@RequestParam String password, HttpServletRequest request, HttpSession session, Model model) {

		Docente docente = docenteServicio.select(email, password);

		if (docente != null) {

			// Tuve que resetear el valor "admin_id" para solucionar un bug extraño con los
			// inicios de sesión, pendiente
			// revisar a profundidad
			if (docente.getAprobado()) {
				request.getSession().setAttribute("admin_id", 0);
				request.getSession().setAttribute("docente_id", docente.getId());
				model.addAttribute("docente", docente);
				return "redirect:/docente/main";
			}

			att.addFlashAttribute("loginError", "El docente aún no se encuentra autorizado para acceder");
			return "redirect:/docente";

		} else {
			att.addFlashAttribute("loginError", "Usuario o contraseña incorrecta");
			return "redirect:/docente";
		}
	}

	@GetMapping("/logout")
	public String cerrarSesionDocente(HttpServletRequest request, HttpSession session, Model model) {
		request.getSession().invalidate();
		return "redirect:/docente/";
	}

	@PostMapping("/save")
	public String guardarDocente(RedirectAttributes att, Docente docente, Model model) {
		docente.setAprobado(false);
		docenteServicio.save(docente);
		att.addFlashAttribute("accion", "Docente registrado con éxito!");
		return "redirect:/docente/";
	}

	@GetMapping("/new")
	public String formularioDeRegistroDocente(Model model) {
		return "register_docente";
	}

	@GetMapping("/main")
	public String seccionPrincipalDocente(HttpServletRequest request, Model model) {
		return "main";
	}

// GESTION DE PLANEADORES

	@GetMapping("/planeadores")
	public String casoPorDefecto(HttpServletRequest request, HttpSession session, Model model) {
//			return "redirect:/docente/planeadores/lista";
		return "redirect:/docente/planeadores/lista";
	}

	@GetMapping("/planeadores/lista")
	public String listaDeInstrumentos(@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos, @ModelAttribute Docente docente,
			Model model) {
//			List<Planeador> planeadors = this.instrumentoServicio.listaDeInstrumentos();
//		Page<Planeador> planeadors = this.planeadorServicio.paginaDePlaneadores(pagina, nroDeElementos);
		Page<Planeador> planeadors = this.planeadorServicio.paginaDePlaneadores(docente, pagina, nroDeElementos);
		Integer totalDePaginas = planeadors.getTotalPages();
		model.addAttribute("paginaDePlaneadores", planeadors);
		model.addAttribute("totalDePaginas", totalDePaginas);
		return "lista_planeadores";
	}


	@GetMapping("/planeadores/nuevo")
	public String formularioDePlaneadores(@ModelAttribute Planeador planeador, HttpServletRequest request,
			HttpSession session, Model model) {
		List<Microcurriculo> listaDeMicrocurriculos = microcurriculoServicio.listaDeMicrocurriculos();
		model.addAttribute("listaDeMicrocurriculos", listaDeMicrocurriculos);
		return "crear_planeador";
	}

	@PostMapping("/planeadores/nuevo")
	public String subirPlaneadores(@ModelAttribute Docente docente, @ModelAttribute Planeador planeador, Model model, RedirectAttributes att) {
		planeador.setDocente(docente);
		this.planeadorServicio.save(planeador);
		att.addFlashAttribute("accion", "Microcurrículo creado con éxito!");
		return "redirect:/docente/planeadores";
	}

// GESTION DE INSTRUMENTOS

	@GetMapping("/planeadores/instrumentos/{planeador_id}")
	public String casoPlaneadoresPorDefecto(@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos, @PathVariable Integer planeador_id, HttpServletRequest request, HttpSession session, Model model) {

		Planeador actual = this.planeadorServicio.findById(planeador_id).get();
		Page<InstrumentoEvaluacion> paginaDeInstrumentos = this.instrumentoServicio.paginaDeInstrumentoEvaluacion(actual, pagina, nroDeElementos);
		model.addAttribute("paginaDeInstrumentos", paginaDeInstrumentos);
		model.addAttribute("planeadorActual", actual);
		return "redirect:/docente/instrumentos/lista";
	}

	
	@GetMapping("/planeadores/instrumentos/{planeador_id}/nuevo")
	public String formularioDeInstrumentos(HttpServletRequest request,
			HttpSession session, @PathVariable Integer planeador_id, @ModelAttribute Planeador planeadorActual, @ModelAttribute InstrumentoEvaluacion instrumento, Model model) {
		Planeador actual = this.planeadorServicio.findById(planeador_id).get();
		model.addAttribute("planeadorActual", actual);
		model.addAttribute("instrumento", new InstrumentoEvaluacion());
		return "crear_instrumento";
	}
	
	@PostMapping("/planeadores/instrumentos/{planeador_id}/nuevo")
	public String subirInstrumento(HttpServletRequest request,
			HttpSession session, @ModelAttribute Planeador planeadorActual, @ModelAttribute InstrumentoEvaluacion instrumento, Model model) {
		this.instrumentoServicio.save(instrumento);
		return "crear_instrumento";
	}

}

//EN PAUSA
//El siguiente código se utilizó en algún punto temprano y se conserva como referencia, pero posiblemente
//sea desechado en el futuro

//	@GetMapping("/perfil")
//	public String elPerfil(HttpServletRequest request, Model model) {
//		return "perfil";
//	}