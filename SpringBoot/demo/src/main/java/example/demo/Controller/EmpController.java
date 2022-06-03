package example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.demo.DAO.EmpDAO;
import example.demo.DTO.Employee;
import example.demo.Service.EmpServiceImp;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class EmpController {
	@Autowired
	EmpServiceImp imp;
	
	@Autowired
	EmpDAO empDAO;

	@GetMapping("/")
	public String getStarted() {
		return "<h2>Get Started</h2>";
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAll() {
		return ResponseEntity.ok(imp.getListEmp());
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Optional<Employee>> getEmpById(@PathVariable Long id) {
		return ResponseEntity.ok(imp.getEmpById(id));
	}

	@GetMapping("/employeesSearch/{name}")
	public ResponseEntity<List<Employee>> findByName(@PathVariable String name) {
		return ResponseEntity.ok(imp.findEmpByName(name));
	}

	@PostMapping("/employees")
	public ResponseEntity<String> createEmp(@RequestBody Employee e) {

		boolean check = imp.createEmp(e);
		if (check) {
			return new ResponseEntity<String>("Tạo thành công", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Đã có lỗi xảy ra", HttpStatus.BAD_REQUEST);

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<String> updateEmp(@PathVariable Long id, @RequestBody Employee e) {
		boolean check = imp.updateEmp(id, e);
		if (check) {
			return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thành công");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đã có lỗi xảy ra");

	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmp(@PathVariable Long id) {
		boolean check = imp.deleteEmp(id);
		if (check) {
			return ResponseEntity.status(HttpStatus.OK).body("Xóa thành công");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đã có lỗi xảy ra");

	}

	@GetMapping("/employees/pagination")
	public ResponseEntity<Map<String, Object>> getAllPagination(@RequestParam(required = false) String searchText,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		try {

			List<Employee> employees = new ArrayList<Employee>();
			Pageable paging = PageRequest.of(page, size);

			Page<Employee> pageEmp;

			if (searchText == null) {
				pageEmp = empDAO.findAll(paging);
			} else {
				pageEmp = empDAO.findByTitleContaining(searchText, paging);
			}

			employees = pageEmp.getContent();
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("employees", employees);
			response.put("currentPage", pageEmp.getNumber());
			response.put("totalItems", pageEmp.getTotalElements());
			response.put("totalPages", pageEmp.getTotalPages());
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}
	
	 @GetMapping("/employees/published")
	  public ResponseEntity<Map<String, Object>> findByPublished(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
	      ) {
	    try {      
	      List<Employee> employees = new ArrayList<Employee>();
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Employee> pageEmp = empDAO.findByPublished(true, paging);
	      employees = pageEmp.getContent();
	            
	      Map<String, Object> response = new HashMap<>();
	      response.put("employees", employees);
	      response.put("currentPage", pageEmp.getNumber());
	      response.put("totalItems", pageEmp.getTotalElements());
	      response.put("totalPages", pageEmp.getTotalPages());
	      
	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

}
