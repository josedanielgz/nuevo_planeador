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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.planeador.servicio.DocenteServicio;
import com.planeador.servicio.InstrumentoServicio;
import com.planeador.servicio.MateriaServicio;
import com.planeador.servicio.PlaneadorServicio;
import com.planeador.modelo.Docente;
import com.planeador.modelo.Materia;
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
	private InstrumentoServicio instrumentoServicio;
	
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
	public String validarInicioSesionDocente(RedirectAttributes att, @RequestParam String email, @RequestParam String password,
			HttpServletRequest request, HttpSession session, Model model) {

		Docente docente = docenteServicio.select(email, password);

		if (docente != null) {

			// Tuve que resetear el valor "admin_id" para solucionar un bug extraño con los inicios de sesión, pendiente
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
//		return "redirect:/docente/planeadores/lista";
		return "redirect:/docente/planeadores/lista";
	}

	@GetMapping("/planeadores/lista")
	public String listaDePlaneadors(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos,
			Model model) {
//		List<Planeador> planeadors = this.planeadorServicio.listaDePlaneadors();
		Page<Planeador> planeadors = this.planeadorServicio.paginaDePlaneadores(pagina,
				nroDeElementos);
		Integer totalDePaginas = planeadors.getTotalPages();
		model.addAttribute("paginaDePlaneadors", planeadors);
		model.addAttribute("totalDePaginas", totalDePaginas);
		return "lista_planeadors";
	}

//	Redirigimos la página actual a donde va a estar el nuevo microcurrículo
	@GetMapping("/planeadores/nuevo")
	public String formularioDePlaneadors(@ModelAttribute Planeador planeador, HttpServletRequest request,
			HttpSession session, Model model) {
		List<Materia> listaDeMaterias = MateriaServicio.listaDeMaterias();
		model.addAttribute("listaDeMaterias", listaDeMaterias);
		return "crear_planeador";
	}

//	Hablamos con el backend para que guarde el planeador
	@PostMapping("/planeadores/nuevo")
	public String subirPlaneadors(@ModelAttribute Planeador planeador, Model model,
			RedirectAttributes att) {
		this.planeadorServicio.save(planeador);
		att.addFlashAttribute("accion", "Microcurrículo creado con éxito!");
		return "redirect:/docente/planeadores";
	}

}

//GESTION DE INSTRUMENTOS

	@GetMapping("/planeadores")
	public String casoPorDefecto(HttpServletRequest request, HttpSession session, Model model) {
//		return "redirect:/docente/planeadores/lista";
		return "redirect:/docente/planeadores/lista";
	}

	@GetMapping("/planeadores/lista")
	public String listaDeInstrumentos(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos,
			Model model) {
//		List<Planeador> planeadors = this.instrumentoServicio.listaDeInstrumentos();
		Page<Planeador> planeadors = this.instrumentoServicio.paginaDePlaneadores(pagina,
				nroDeElementos);
		Integer totalDePaginas = planeadors.getTotalPages();
		model.addAttribute("paginaDeInstrumentos", planeadors);
		model.addAttribute("totalDePaginas", totalDePaginas);
		return "lista_planeadors";
	}

//	Redirigimos la página actual a donde va a estar el nuevo microcurrículo
	@GetMapping("/planeadores/nuevo")
	public String formularioDeInstrumentos(@ModelAttribute Planeador planeador, HttpServletRequest request,
			HttpSession session, Model model) {
		List<Materia> listaDeMaterias = MateriaServicio.listaDeMaterias();
		model.addAttribute("listaDeMaterias", listaDeMaterias);
		return "crear_planeador";
	}

//	Hablamos con el backend para que guarde el planeador
	@PostMapping("/planeadores/nuevo")
	public String subirInstrumentos(@ModelAttribute Planeador planeador, Model model,
			RedirectAttributes att) {
		this.instrumentoServicio.save(planeador);
		att.addFlashAttribute("accion", "Microcurrículo creado con éxito!");
		return "redirect:/docente/planeadores";
	}

}
	

//EN PAUSA
//El siguiente código se utilizó en algún punto temprano y se conserva como referencia, pero posiblemente
//sea desechado en el futuro
	
//	@GetMapping("/perfil")
//	public String elPerfil(HttpServletRequest request, Model model) {
//		return "perfil";
//	}