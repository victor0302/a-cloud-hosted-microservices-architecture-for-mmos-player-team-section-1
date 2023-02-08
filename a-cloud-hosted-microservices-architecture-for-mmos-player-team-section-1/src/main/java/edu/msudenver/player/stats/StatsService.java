package edu.msudenver.player.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.msudenver.player.account.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The service for managing {@link Account}'s experience (Stats) from killing Monsters.
 */
@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Returns the list of ALL experience (Stats) regardless of {@link Account}.
     * @return the list of ALL experience (Stats) regardless of {@link Account}
     */
    public List<Stats> getStats() {
        return statsRepository.findAll();
    }

    /**
     * Returns the list of experience (Stats) for the specified unique identifier of the {@link Account}.
     * @param playerId the unique identifier of the {@link Account} whose experience (Stats) will be returned
     * @return the list of experience (Stats) for the specified unique identifier of the {@link Account}
     */
    public List<Stats> getPlayersStats(Long playerId)
    {
        try {
            return statsRepository.findByPlayerId(playerId);
        } catch(NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the {@link Account}'s experience (Stats) from killing Monsters.
     * @param id the unique identifier of the {@link Account}'s experience (Stats)
     * @return the {@link Account}'s experience (Stats)
     */
    public Stats getStats(Long id) {
        try {
            return statsRepository.findById(id).get();
        } catch(NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save the specified {@link Account}'s experience (Stats) from killing Monsters.
     * @param stats the {@link Account}'s experience (Stats) to create
     * @return the {@link Account}'s experience (Stats)
     */
    @Transactional
    public Stats saveStats(Stats stats) {
        stats = statsRepository.saveAndFlush(stats);
        entityManager.refresh(stats);
        return stats;
    }

}