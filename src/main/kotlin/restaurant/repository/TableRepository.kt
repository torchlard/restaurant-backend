package restaurant.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import restaurant.domain.Tables

@Repository
interface TableRepository: JpaRepository<Tables,Long>, JpaSpecificationExecutor<Tables> {


}


