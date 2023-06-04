package com.planeador.controlador;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
import org.springframework.boot.CommandLineRunner;
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
import com.planeador.utils.PasswordHasher;

//Este controlador administra todas las rutas del aplicativo que se van como /admin/{lo que sea}
@Controller
@RequestMapping("/admin")
public class AdministradorControlador {

	@Autowired
	private AdministradorServicio administradorServicio;
	@Autowired
	private DocenteServicio docenteServicio;
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
				admin = this.administradorServicio.get(admin_id);
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
			@RequestParam String password, HttpServletRequest request, HttpSession session, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
// En lo posible quiero terminar de implementar el hasheado de la contraseña, pero necesito resolver ese bug de
// la persistencia primero
//		String new_password = PasswordHasher.generateStorngPasswordHash(password);
//		Administrador admin = administradorServicio.select(email, new_password);
		Administrador admin = administradorServicio.select(email, password);
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
	public String guardarAdmin(RedirectAttributes att, Administrador admin) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String nueva_contraseña = PasswordHasher.generateStorngPasswordHash(admin.getPassword());
		admin.setPassword(nueva_contraseña);
		administradorServicio.save(admin);
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
		Page<Docente> solicitudesPaginaPrincipal = this.docenteServicio.paginaDeSolicitudes(1, 5);
		model.addAttribute("solicitudes", solicitudesPaginaPrincipal);
		Page<Materia> materiasPaginaPrincipal = MateriaServicio.paginaDeMaterias(1, 5);
		model.addAttribute("materias", materiasPaginaPrincipal);
		return "main";
	}

	@GetMapping("/solicitudes")
	public String listarSolicitudesDocente(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos,
			Model model) {
		Page<Docente> paginaDeSolicitudes = this.docenteServicio.paginaDeSolicitudes(1, 1);
		int numeroDePaginas = paginaDeSolicitudes.getTotalPages();
		model.addAttribute("paginaDeSolicitudes", paginaDeSolicitudes);
		model.addAttribute("totalDePaginas", numeroDePaginas);
		return "solicitudes";
	}

	@GetMapping("/solicitudes/aprobar/{id}")
	public String aprobarSolicitudDocente(@PathVariable Integer id, Model model) {
		Docente porAprobar = this.docenteServicio.get(id);
		porAprobar.setAprobado(true);
//		Aparentemente este método también actualiza
		docenteServicio.save(porAprobar);
		model.addAttribute("msjAprobacion", "Solicitud aprobada con éxito");
		return "redirect:/admin/solicitudes/";

	}

	@GetMapping("/solicitudes/rechazar/{id}")
	public String rechazarSolicitudDocente(@PathVariable Integer id, Model model) {
		this.docenteServicio.delete(id);
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
		Page<Materia> paginaDeMaterias = MateriaServicio.paginaDeMaterias(pagina, nroDeElementos);
		// Indica el número total de páginas totales en que se puede dividir toda la
		// lista de objetos traía de la BBDD
		int totalDePaginas = paginaDeMaterias.getTotalPages();
		model.addAttribute("paginaDeMaterias", paginaDeMaterias);
		model.addAttribute("totalDePaginas", totalDePaginas);
		return "materias";
	}

	@GetMapping("/microcurriculos")
	public String casoPorDefecto(HttpServletRequest request, HttpSession session, Model model) {
//		return "redirect:/admin/microcurriculos/lista";
		return "redirect:/admin/microcurriculos/lista";
	}

	@GetMapping("/microcurriculos/lista")
	public String listaDeMicrocurriculos(
			@RequestParam(value = "pagina", required = false, defaultValue = "1") int pagina,
			@RequestParam(value = "nroDeElementos", required = false, defaultValue = "5") int nroDeElementos,
			Model model) {
//		List<Microcurriculo> microcurriculos = this.microcurriculoServicio.listaDeMicrocurriculos();
		Page<Microcurriculo> microcurriculos = this.microcurriculoServicio.paginaDeMicrocurriculos(pagina,
				nroDeElementos);
		Integer totalDePaginas = microcurriculos.getTotalPages();
		model.addAttribute("paginaDeMicrocurriculos", microcurriculos);
		model.addAttribute("totalDePaginas", totalDePaginas);
		return "lista_microcurriculos";
	}

//	Redirigimos la página actual a donde va a estar el nuevo microcurrículo
	@GetMapping("/microcurriculos/nuevo")
	public String formularioDeMicrocurriculos(@ModelAttribute Microcurriculo microcurriculo, HttpServletRequest request,
			HttpSession session, Model model) {
		List<Materia> listaDeMaterias = MateriaServicio.listaDeMaterias();
		model.addAttribute("listaDeMaterias", listaDeMaterias);
		return "crear_microcurriculo";
	}

//	Hablamos con el backend para que guarde el microcurriculo
	@PostMapping("/microcurriculos/nuevo")
	public String subirMicrocurriculos(@ModelAttribute Microcurriculo microcurriculo, Model model,
			RedirectAttributes att) {
		this.microcurriculoServicio.save(microcurriculo);
		att.addFlashAttribute("accion", "Microcurrículo creado con éxito!");
		return "redirect:/admin/microcurriculos";
	}

}

//EN PAUSA
//El siguiente código se utilizó en algún punto temprano y se conserva como referencia, pero posiblemente
//sea desechado en el futuro

//Este código funciona pero decidimos usar una solución más fácil de entender y de paso implementar paginación. Despliega la lista
//de solicitudes disponibles en el sistema.
//@GetMapping("/solicitudes")
//public String listarSolicitudesDocente(HttpServletRequest request, Model model) {
//
////	https://stackoverflow.com/questions/50348166/java-8-list-of-objects-to-mapstring-list-of-values
////	https://www.appsloveworld.com/springboot/100/35/how-can-i-create-dynamic-table-in-thymeleaf
////	El siguiente acomodo intenta generar la tabla de manera dinámica pasándole a la plantilla el nombre de las columnas y usando
////	un Map para asociarlo con los atributos correspondientes de cada objeto docente. Funciona de momento.
//	List<Docente> solicitudes = this.docenteServicio.findPendingRequests();
//	List<String> headers = Arrays.asList("Nombre", "Email", "Aprobación");
//
//	List<Map<String, Object>> rows = solicitudes.stream().map(docente -> {
//		Map<String, Object> um = new HashMap<>();
//		um.put(headers.get(0), docente.getNombre());
//		um.put(headers.get(1), docente.getEmail());
//		um.put(headers.get(2), docente.getId());
//		return um;
//	}).collect(Collectors.toList());
//
//	model.addAttribute("headers", headers);
//	model.addAttribute("rows", rows);
//	return "solicitudes";
//}

//Controlador principal original, utilizado para redigir el inicio de sesión del administrador hacia la página principal,
//pero sin la información acerca de los docentes en el sistema y las solicitudes recientes
//@GetMapping("/main")
//public String seccionPrincipalAdmin(HttpServletRequest request, Model model) {
//	return "main";
//}

//@GetMapping("/perfil")
//public String elPerfil(HttpServletRequest request, Model model) {
//	Testeando el nuevo @ModelAttribute
//	int admin_id = (int) request.getSession().getAttribute("admin_id");
//	Administrador adm = this.administradorServicio.get(admin_id);
//	model.addAttribute("admin", adm);
//	return "perfil";
//}

//@GetMapping("/materias")
//public String listaDeMaterias (Model model){
//	List<Materia> materias = this.materiaServicio.getAll();
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
