package ru.nextupvamp.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class InputHandlerTest {
    // Correctness of all the input is handled by
    // inputNumber method. It prohibits the input
    // of not allowed values. If it works properly,
    // not allowed values will never appear. So
    // we'll test only that method. And we'll test
    // if class methods throw exception if for
    // some reason prohibited value was appeared.

    @Mock
    private Printer printerMock;
    // using printer mock in the inputHandlerSpy causes NPE
    @Spy
    private InputHandler inputHandlerSpy = new InputHandler(System.in,
            new Printer(new PrintStream(new ByteArrayOutputStream())));

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    // Correct values are [0, 10]
    public void testInputCorrectNumber(String input) {
        InputStream inputStream = new ByteArrayInputStream(("-1" + System.lineSeparator() + input).getBytes());
        InputHandler inputHandler = new InputHandler(inputStream, printerMock);
        int number = inputHandler.inputNumber(0, 10);
        // surely properly working method
        int expected = Integer.parseInt(input);

        // check if method returned correct value but not the wrong one that was entered first
        assertEquals(expected, number);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-10", "-5", "-1", "11", "a", "b"})
    // Correct values are [0, 10]
    public void testInputWrongNumberInRange(String input) {
        InputStream is = new ByteArrayInputStream((input + System.lineSeparator() + "0").getBytes());
        InputHandler inputHandler = new InputHandler(is, printerMock);
        int number = inputHandler.inputNumber(0, 10);

        // check if method returned allowed value that was entered after the wrong one
        assertEquals(0, number);
    }

    @Test
    public void testInputWrongMazeParams() {
        doReturn(-1).when(inputHandlerSpy).inputNumber(anyInt(), anyInt());
        assertThrows(IllegalStateException.class, () -> inputHandlerSpy.inputMazeParams());
    }

    @Test
    public void testInputWrongPathfinderType() {
        doReturn(-1).when(inputHandlerSpy).inputNumber(anyInt(), anyInt());
        assertThrows(IllegalStateException.class, () -> inputHandlerSpy.inputPathfinderType());
    }

    @Test
    public void testInputWrongBuilderType() {
        doReturn(-1).when(inputHandlerSpy).inputNumber(anyInt(), anyInt());
        assertThrows(IllegalStateException.class, () -> inputHandlerSpy.inputBuilderType());
    }

    @Test
    public void testInputWrongEntranceCoords() {
        doReturn(-1).when(inputHandlerSpy).inputNumber(anyInt(), anyInt());
        assertThrows(IllegalStateException.class, () -> inputHandlerSpy.inputEntrancePoints(1, 1));
    }
}