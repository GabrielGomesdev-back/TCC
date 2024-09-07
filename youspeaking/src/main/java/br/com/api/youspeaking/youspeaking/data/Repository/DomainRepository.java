package br.com.api.youspeaking.youspeaking.data.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.youspeaking.youspeaking.data.Entity.DomainEntity;

public interface DomainRepository extends JpaRepository<DomainEntity, Long> {
    
}
