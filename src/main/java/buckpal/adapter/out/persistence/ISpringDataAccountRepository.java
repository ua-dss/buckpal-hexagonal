package buckpal.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface ISpringDataAccountRepository extends JpaRepository<AccountJpaEntity, Long> {
}
