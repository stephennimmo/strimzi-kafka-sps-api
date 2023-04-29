package io.strimzi.kafka.sps.dvm.system;

import io.strimzi.kafka.sps.exception.ServiceException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SystemService {

    public static final Logger logger = LoggerFactory.getLogger(SystemService.class);
    
    private final SystemRepository systemRepository;
    private final SystemMapper systemMapper;

    public SystemService(SystemRepository systemRepository, SystemMapper systemMapper) {
        this.systemRepository = systemRepository;
        this.systemMapper = systemMapper;
    }

    public List<System> findAll() {
        return this.systemMapper.toDomainList(systemRepository.findAll().list());
    }

    public Optional<System> findByName(@NotNull String name) {
        return systemRepository.find("name", name).firstResultOptional()
                .map(systemMapper::toDomain);
    }

    @Transactional
    public void save(@Valid System system) {
        logger.debug("Saving System: {}", system);
        if (this.findByName(system.name()).isPresent()) {
            throw new ServiceException("System with name[%s] already exists", system.name());
        }
        SystemEntity entity = systemMapper.toEntity(system);
        systemRepository.persist(entity);
        systemMapper.updateDomainFromEntity(entity, system);
    }

    @Transactional
    public void update(@NotNull String name, @Valid System system) {
        logger.debug("Updating System: {}", system);
        SystemEntity entity = systemRepository.find("name", name).firstResultOptional()
                .orElseThrow(() -> new ServiceException("No System found for name[%s]", system.name()));
        systemMapper.updateEntityFromDomain(system, entity);
        systemRepository.persist(entity);
        systemMapper.updateDomainFromEntity(entity, system);
    }

}
