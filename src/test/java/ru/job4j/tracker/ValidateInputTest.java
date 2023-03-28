package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ValidateInputTest {

    @Test
    public void whenInvalidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"one", "1"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        String expected = "Please enter validate data again." + System.lineSeparator();
        assertThat(selected, is(1));
        assertThat(out.toString(), is(expected));
    }

    @Test
    public void whenValidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"10"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(10));
    }

    @Test
    public void whenValidManyTimesInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"7", "test", "3"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int first = input.askInt("Enter menu: ");
        String askStr = input.askStr("Enter name: ");
        int second = input.askInt("Enter menu: ");
        assertThat(first, is(7));
        assertThat(askStr, is("test"));
        assertThat(second, is(3));
    }

    @Test
    public void whenValidInputIsNegativeNumber() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"-1"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(-1));
    }
}
