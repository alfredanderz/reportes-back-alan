package utez.edu.mx.communitycommitteesystem.model.status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusBean, Long> {
    Optional<StatusBean> findById(Long id);
}
