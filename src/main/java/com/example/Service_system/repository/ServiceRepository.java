package com.example.Service_system.repository;
import com.example.Service_system.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {


//    public List<Service> loadAll(){
//        Query query = getEntityManager().createQuery(" FROM Service ");
//        return query.getResultList();
//    }
//    public List<String> serviceNames(){
//        Query serviceNames = getEntityManager().createQuery("SELECT name FROM Service ");
//        return serviceNames.getResultList();
//    }
}
