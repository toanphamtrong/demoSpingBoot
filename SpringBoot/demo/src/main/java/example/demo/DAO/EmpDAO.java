package example.demo.DAO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import example.demo.DTO.Employee;

@Repository
public interface EmpDAO extends JpaRepository<Employee, Long>{
	// cho phép Select, Update, Delete
	
	// search by name
	@Query("select e from Employee e where lower(e.firstname) like lower(concat('%', :name, '%')) " +
			"or lower(e.lastname) like lower(concat('%', :name, '%'))")
	public List<Employee> findByName(@Param("name") String name);
	
	Page<Employee> findAll(Pageable pageable); 
	Page<Employee> findByPublished(boolean published, Pageable pageable);
	
	@Query("select e from Employee e where lower(e.firstname) like lower(concat('%', :searchText, '%')) " +
			"or lower(e.lastname) like lower(concat('%', :searchText, '%'))")
	Page<Employee> findByTitleContaining(String searchText, Pageable pageable);
}

//Tất cả các loại repository trong Spring Data đều extends từ một thằng interface chung, 
//tên là Repository interface
//Nó cung cấp các phương thức CRUD cơ bản như thêm, sửa, xóa, tìm kiếm .....
//JpaRepository interface extends PagingAndSortingRepository
//PagingAndSortingRepository extends CrudRepository.
