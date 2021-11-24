package com.example.membermanage.repository;

import com.example.membermanage.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Member, Integer> {
    
}
