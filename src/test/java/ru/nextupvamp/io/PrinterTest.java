package ru.nextupvamp.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nextupvamp.TestSourceData;
import ru.nextupvamp.pathfinders.DFSPathfinder;
import ru.nextupvamp.pathfinders.Pathfinder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrinterTest {
    public static final ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
    public static final PrintStream PRINT_STREAM = new PrintStream(BAOS);
    public static final Printer PRINTER = new Printer(PRINT_STREAM);
    @Mock
    private Pathfinder pathfinder = new DFSPathfinder();

    @AfterEach
    @SneakyThrows
    public void flushStream() {
        BAOS.reset();
    }

    @Test
    public void testUnicursalMazePrint() {
        PRINTER.printMaze(TestSourceData.UNICURSAL_MAZE);
        String output = BAOS.toString();
        assertEquals(TestSourceData.UNICURSAL_MAZE_DEPICTION, output);
    }

    @Test
    public void testBraidMazeWithModifiersPrint() {
        PRINTER.printMaze(TestSourceData.BRAID_MAZE_WITH_MODIFIERS);
        String output = BAOS.toString();
        assertEquals(TestSourceData.BRAID_MAZE_WITH_MODIFIERS_DEPICTION, output);
    }

    @Test
    public void testPrintPathOnUnicursalMaze() {
        when(pathfinder.findPath(any(), any(), any())).thenReturn(TestSourceData.UNICURSAL_MAZE_DFS_PATH);
        PRINTER.printMaze(TestSourceData.UNICURSAL_MAZE, pathfinder);
        String output = BAOS.toString();
        assertEquals(TestSourceData.UNICURSAL_MAZE_WITH_PATH_DEPICTION, output);
    }

    @Test
    public void testPathNotFound() {
        when(pathfinder.findPath(any(), any(), any())).thenReturn(Collections.emptyList());
        PRINTER.printMaze(TestSourceData.UNICURSAL_MAZE, pathfinder);
        String output = BAOS.toString();
        assertEquals("Путь не найден" + TestSourceData.LINE_SEPARATOR, output);
    }
}