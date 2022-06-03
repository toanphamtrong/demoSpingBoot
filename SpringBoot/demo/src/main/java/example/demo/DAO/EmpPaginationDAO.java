package example.demo.DAO;

import org.springframework.data.repository.PagingAndSortingRepository;

import example.demo.DTO.Employee;

public interface EmpPaginationDAO extends PagingAndSortingRepository<Employee, Long>{

}
