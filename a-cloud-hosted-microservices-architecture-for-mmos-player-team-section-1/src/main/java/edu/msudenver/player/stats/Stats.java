package edu.msudenver.player.stats;

import edu.msudenver.player.account.Account;

import javax.persistence.*;

/**
 * Represents a {@link Account}'s experience (Stats) from killing Monsters.
 */
@Entity
@Table(name = "stats")
public class Stats {
    @Id
    @GeneratedValue // default: (strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "playerId")
    private Long playerId;

    @Column(name = "monsterId")
    private Long monsterId;

    @Column(name = "points")
    private int points;

    /**
     * The default constructor.
     */
    public Stats() {
        // no-op
    }

    /**
     * Create a {@link Account}'s experience (Stats) from killing Monsters.
     * @param playerId  the unique identifier of the {@link Account}
     * @param monsterId the unique identifier of the Monster the {@link Account} killed
     * @param points    the experience points the {@link Account} earned from killing the Monster
     */
    public Stats(Long playerId, Long monsterId, int points) {
        this.playerId = playerId;
        this.monsterId = monsterId;
        this.points = points;
    }

    /**
     * Returns the unique identifier of a {@link Account}'s Stats from killing a Monster.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Returns the unique identifier of the {@link Account}.
     */
    public Long getPlayerId() {
        return this.playerId;
    }

    /**
     * Set the unique identifier of a {@link Account}'s Stats from killing a Monster.
     * @param id the unique identifier of a {@link Account}'s Stats from killing a Monster
     */
    public void setPlayerId(long id) {
        this.playerId = id;
    }

    /**
     * Returns the unique identifier of the Monster the {@link Account} killed.
     * @return the unique identifier of the Monster the {@link Account} killed
     */
    public Long getMonsterId() {
        return monsterId;
    }

    /**
     * Set the unique identifier of the Monster the {@link Account} killed.
     * @param id the unique identifier of the Monster the {@link Account} killed
     */
    public void setMonsterId(long id) {
        this.monsterId = id;
    }

    /**
     * Returns the experience points the {@link Account} earned from killing the Monster.
     * @return the experience points the {@link Account} earned from killing the Monster
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set the experience points the {@link Account} earned from killing the Monster.
     * @param points the experience points the {@link Account} earned from killing the Monster
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
