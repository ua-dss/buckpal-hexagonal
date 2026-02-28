package buckpal.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface IAccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
}
