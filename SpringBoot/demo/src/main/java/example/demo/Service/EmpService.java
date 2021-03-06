package example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import example.demo.DTO.Employee;

public interface EmpService {
	
	
	public List<Employee> getListEmp();
	public Employee getEmp(Long id);
	public Optional<Employee> getEmpById(Long id);
	public boolean createEmp(Employee emp);
	public boolean updateEmp(Long id, Employee emp);
	public boolean deleteEmp(Long id);
	public List<Employee> findEmpByName(String name);
}
