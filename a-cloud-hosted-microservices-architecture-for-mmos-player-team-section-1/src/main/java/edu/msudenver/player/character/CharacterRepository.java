package edu.msudenver.player.character;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {
}
