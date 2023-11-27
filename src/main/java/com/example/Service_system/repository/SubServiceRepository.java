package com.example.Service_system.repository;
import com.example.Service_system.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {

//    public List<String> listOfSubServiceNames() {
//        Query subServiceNames = getEntityManager().createQuery("SELECT name FROM SubService");
//        return subServiceNames.getResultList();
//    }
//
//    public List<SubService> loadAll() {
//        Query allOfSubService = getEntityManager().createQuery(" FROM SubService");
//        return allOfSubService.getResultList();
//    }
}
