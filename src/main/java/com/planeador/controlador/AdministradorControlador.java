package com.planeador.controlador;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;
import java.util.stream.Collectors;

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

import com.planeador.servicio.AdministradorServicio;
import com.planeador.servicio.DocenteServicio;
import com.planeador.servicio.MateriaServicio;
import com.planeador.servicio.MicrocurriculoServicio;
import com.planeador.modelo.Administrador;
import com.planeador.modelo.Docente;
import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;

//Este controlador administra todas las rutas del aplicativo que se van como /admin/{lo que sea}
@Controller
@RequestMapping("/admin")
public class AdministradorControlador {

	@Autowired
	private AdministradorServicio administradorService;
	@Autowired
	private DocenteServicio docenteService;
	@Autowired
	private MateriaServicio MateriaServicio;

	@Autowired
	private MicrocurriculoServicio microcurriculoServicio;

//	De manera temporal para rastrear el objeto Admin a lo largo de la sesión, buscar una
//	solución más estable en el futuro

	@ModelAttribute("admin")
	public Administrador getAdministradorActual(Model model, HttpServletRequest request) {
		Administrador admin = (Administrador) model.getAttribute("admin");
		if (admin == null || admin.isEmpty()) {
			int admin_id;
			try {
				admin_id = (int) request.getSession().getAttribute("admin_id");
			} catch (NullPointerException e) {
				admin_id = 0;
			}
			if (admin_id == 0) {
				admin = new Administrador();
			} else {
				admin = this.administradorService.get(admin_id);
			}
			model.addAttribute("admin", admin);
		}
		return admin;
	}

	@GetMapping("")
	public String rutaPorDefectoAdministrador(HttpServletRequest request, HttpSession session, Model model) {
		if (request.getSession().getAttribute("admin_id") != null) {
			return "main";
		} else
			return "login_admin";
	}

	@PostMapping("/login")
	public String validarInicioSesionAdmin(RedirectAttributes att, @RequestParam String email,
			@RequestParam String password, HttpServletRequest request, HttpSession session, Model model) {

		Administrador admin = administradorService.select(email, password);

		if (admin != null) {

			// Tuve que resetear el valor "docente_id" para solucionar un bug extraño con
			// los inicios de sesión, pendiente
			// revisar a profundidad
			request.getSession().setAttribute("docente_id", 0);
			request.getSession().setAttribute("admin_id", admin.getId());
			model.addAttribute("admin", admin);
			return "redirect:/admin/main";
		} else {
			att.addFlashAttribute("loginError", "Usuario o contraseña incorrecta");
			return "redirect:/admin";
		}
	}

	@GetMapping("/logout")
	public String cerrarSesionAdmin(HttpServletRequest request, HttpSession session) {
		request.getSession().invalidate();
		return "redirect:/admin/";
	}

	@PostMapping("/save")
	public String guardarAdmin(RedirectAttributes att, Administrador admin) {
		administradorService.save(admin);
		att.addFlashAttribute("accion", "Administrador registrado con éxito!");
		return "redirect:/admin/";
	}

//	A desactivar en un futuro cuando no necesitemos crear más administradores.
	@GetMapping("/new")
	public String formularioRegistroAdmin(Model model) {
		return "register_admin";
	}

	@GetMapping("/main")
	public String seccionPrincipalAdmin(HttpServletRequest request, Model model) {
		return "main";
	}

	@GetMapping("/solicitudes")
	public String listarSolicitudesDocente(HttpServletRequest request, Model model) {

//		https://stackoverflow.com/questions/50348166/java-8-list-of-objects-to-mapstring-list-of-values
//		https://www.appsloveworld.com/springboot/100/35/how-can-i-create-dynamic-table-in-thymeleaf
//		El siguiente acomodo intenta generar la tabla de manera dinámica pasándole a la plantilla el nombre de las columnas y usando
//		un Map para asociarlo con los atributos correspondientes de cada objeto docente. Funciona de momento.
		List<Docente> solicitudes = this.docenteService.findPendingRequests();
		List<String> headers = Arrays.asList("Nombre", "Email", "Aprobación");

		List<Map<String, Object>> rows = solicitudes.stream().map(docente -> {
			Map<String, Object> um = new HashMap<>();
			um.put(headers.get(0), docente.getNombre());
			um.put(headers.get(1), docente.getEmail());
			um.put(headers.get(2), docente.getId());
			return um;
		}).collect(Collectors.toList());

		model.addAttribute("headers", headers);
		model.addAttribute("rows", rows);
		return "solicitudes";
	}

	@GetMapping("/aprobar/{id}")
	public String aprobarSolicitudDocente(@PathVariable Integer id, Model model) {
		Docente porAprobar = this.docenteService.get(id);
		porAprobar.setAprobado(true);
//		Aparentemente este método también actualiza
		docenteService.save(porAprobar);
		model.addAttribute("msjAprobacion", "Solicitud aprobada con éxito");
		return "redirect:/admin/solicitudes/";

	}

	@GetMapping("/rechazar/{id}")
	public String rechazarSolicitudDocente(@PathVariable Integer id, Model model) {
		this.docenteService.delete(id);
		model.addAttribute("msjRechazo", "Solicitud aprobada con éxito");
		return "redirect:/admin/solicitudes/";

	}

//	https://www.kindsonthegenius.com/part-2-how-to-implement-pagination-in-spring-boot-with-thymeleaf/
	@GetMapping("/materias")
	public String buscarPaginaDeMaterias(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos,
			Model model) {
//		Utiliza un método del servicio para extraer una consulta paginada de la lista de objetos almacenados
//		en la BBDD. El objeto Page tiene varias propiedades que son de utilidad en la plantilla final
		Page<Materia> paraElControlador = MateriaServicio.paginaDeMaterias(pagina, nroDeElementos);
		// Indica el número total de páginas totales en que se puede dividir toda la
		// lista de objetos traía de la BBDD
		int totalPages = paraElControlador.getTotalPages();
		model.addAttribute("rows", paraElControlador);
		model.addAttribute("pageNumbers", totalPages);
		return "materias";
	}

	@GetMapping("/microcurriculos")
	public String casoDefault(HttpServletRequest request, HttpSession session, Model model) {
//		return "redirect:/admin/microcurriculos/lista";
		return "lista_microcurriculos";
	}

	@GetMapping("/microcurriculos/lista")
	public String listaDeMicrocurriculos(HttpServletRequest request, Model model) {
		List<Microcurriculo> microcurriculos = this.microcurriculoServicio.listaDeMicrocurriculos();
		model.addAttribute("microcurriculos", microcurriculos);
		return "lista_microcurriculos";
	}

//	Redirigimos la página actual a donde va a estar el nuevo microcurrículo
	@GetMapping("/microcurriculos/nuevo")
	public String formularioDeMicrocurriculos(HttpServletRequest request, HttpSession session, Model model) {
		return "crear_microcurriculo";
	}

//	Hablamos con el backend para que guarde el microcurriculo
	@PostMapping("/microcurriculos/nuevo")
	public String subirMicrocurriculos(@ModelAttribute Microcurriculo microcurriculo, Model model, RedirectAttributes att) {
		this.microcurriculoServicio.save(microcurriculo);
		att.addFlashAttribute("accion", "Microcurrículo creado con éxito!");
		return "redirect:/admin/microcurriculos";
	}

}

//EN PAUSA
//El siguiente código se utilizó en algún punto temprano y se conserva como referencia, pero posiblemente
//sea desechado en el futuro

//@GetMapping("/perfil")
//public String elPerfil(HttpServletRequest request, Model model) {
//	Testeando el nuevo @ModelAttribute
//	int admin_id = (int) request.getSession().getAttribute("admin_id");
//	Administrador adm = this.administradorService.get(admin_id);
//	model.addAttribute("admin", adm);
//	return "perfil";
//}

//@GetMapping("/materias")
//public String listaDeMaterias (Model model){
//	List<Materia> materias = this.materiaService.getAll();
//	 List<String> headers = Arrays.asList("Nombre","# de Créditos","Semestre", "Acción");
//	// List<Map<String, Object>> rows = materias.stream().map(materia -> {
//	// 	Map<String, Object> um = new HashMap<>();
//	// 	um.put(headers.get(0), materia.getNombre());
//	// 	um.put(headers.get(1), materia.getCreditos());
//	// 	um.put(headers.get(2), materia.getSemestre());
//	// 	um.put(headers.get(2), materia.getId());
//	// 	return um;
//	// }).collect(Collectors.toList());
//	 model.addAttribute("headers", headers);
//	// model.addAttribute("rows", rows);
//	model.addAttribute("rows", materias);
//	return "materias";
//}

//@GetMapping("login")
//public String loginPage(HttpServletRequest request, HttpSession session, Model model) {
////	request.getSession().setAttribute("es_admin", true);
//	request.getSession().invalidate();
//	return "login_admin";
//}
