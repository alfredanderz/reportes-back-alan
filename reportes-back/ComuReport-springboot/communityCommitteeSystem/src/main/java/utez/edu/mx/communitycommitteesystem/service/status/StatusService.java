package utez.edu.mx.communitycommitteesystem.service.status;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utez.edu.mx.communitycommitteesystem.model.status.StatusBean;
import utez.edu.mx.communitycommitteesystem.model.status.StatusRepository;

@Service
@AllArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusBean findById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));
    }
}
