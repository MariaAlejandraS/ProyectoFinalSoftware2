package co.edu.unicauca.empresa_service.infrastructure.pesistence;
// ---------------------------------------------
// EmpresaJpaRepository.java (Repositorio JPA)
// DAO que extiende JpaRepository de Spring Data
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaJpaRepository extends JpaRepository<EmpresaJpaEntity, String> {
}
