import java.util.Objects;
import java.util.Scanner;

public interface Command {

    String name();
    boolean run(Scanner scanner);

}
