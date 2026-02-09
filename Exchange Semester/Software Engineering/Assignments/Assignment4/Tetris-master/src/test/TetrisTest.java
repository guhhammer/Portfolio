package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psnbtech.BoardPanel;
import org.psnbtech.Tetris;

class TetrisTest {

	private BoardPanel bp;
	private Tetris tetris;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		bp = new BoardPanel(tetris);
	}

	@AfterEach
	void tearDown() throws Exception {
		bp = null;
	}

	
	@Test
	void updateGameTest() {
		
		
		/*
		 * 
		 * Checks if it updates the score when lines are cleared.
		 * 
		 * 
		 * */
		
		
		int cleared = bp.checkLines();
		
		int prev_score = tetris.getScore();
		
		tetris.update_p();
		
		assertEquals(true, (cleared > 0) == (prev_score != tetris.getScore()));
		
	}

}
