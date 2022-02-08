package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psnbtech.BoardPanel;
import org.psnbtech.Tetris;
import org.psnbtech.TileType;

class BoardPanelTest {
	
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
	void testIsValidAndEmpty() {
		
		/*
		 * 
		 * Tests if its a valid position.
		 * 
		 * 
		 * */
		
		
		TileType type = tetris.getPieceType();
		int pieceCol = tetris.getPieceCol();
		int pieceRow = tetris.getPieceRow();
		int rotation = tetris.getPieceRotation();
		
		boolean ok = true;
		for(int lowest = pieceRow; lowest < bp.ROW_COUNT; lowest++) {
			//If no collision is detected, try the next row.
			if(!bp.isValidAndEmpty(type, pieceCol, lowest, rotation)) {	
				ok = false;
				break;
			}
		}
		
		assertEquals(ok, true);
		
	}

	@Test
	void testAddPiece() {
		
		
		/*
		 * Checks if it overwrites the tiles array if the tile added has 0 rotation.
		 * 
		 * */
		
		TileType[][] tiles = bp.getTileTest();
		
		
		TileType before = null; boolean overwrite = false;
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				
				before = tiles[i][j];
				
				bp.addPiece(tiles[i][j], i, j, 0);
				
				if(bp.getTileAtTest(i, j) == before) { overwrite = true; }
				
			}
		}
		
		
		assertEquals(false, overwrite);
		
	}

	@Test
	void testCheckLines() {
		
		/*
		 * Checks if the update has cleaned the lines.
		 * 
		 * 
		 * */
		
		
		int lastCheck = bp.checkLines();
		
		tetris.update_p();
		
		assertEquals(true, bp.checkLines() != lastCheck && bp.checkLines() == 0);
		
	}

}
