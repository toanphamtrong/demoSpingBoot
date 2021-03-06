package example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.demo.DAO.EmpDAO;
import example.demo.DTO.Employee;

@Service
public class EmpServiceImp implements EmpService {

	@Autowired
	private EmpDAO empDAO;

	@Override
	public List<Employee> getListEmp() {
		return empDAO.findAll();
	}

	@Override
	public Employee getEmp(Long id) {
		return empDAO.getById(id);
	}
	
	
	@Override
	public Optional<Employee> getEmpById(Long id) {
		return empDAO.findById(id);
	}
	
	
	@Override
	public boolean createEmp(Employee emp) {
		if (!empDAO.existsById(emp.getId())) {
			empDAO.save(emp);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateEmp(Long id, Employee emp) {
		if (empDAO.existsById(id)) {
			empDAO.save(emp);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteEmp(Long id) {
		if (empDAO.existsById(id)) {
			empDAO.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Employee> findEmpByName(String name) {
		return empDAO.findByName(name);
	}

}
