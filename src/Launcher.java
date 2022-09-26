import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Launcher {

    public static class fibo implements Command{
        @Override
        public String name() {
            return "Fibo";
        }

        int fibonacci(int n)
        {
            if(n == 0)
                return 0;
            else if(n == 1)
                return 1;
            else
                return fibonacci(n - 1) + fibonacci(n - 2);
        }
        @Override
        public boolean run(Scanner scanner) {
            int fibo_number;

            try {
                System.out.println("Number for fibo  : ");
                String line = scanner.nextLine();
                fibo_number = Integer.parseInt(line);
                int res = fibonacci(fibo_number);
                System.out.println(res);

            } catch (NumberFormatException e) {
                System.out.println("Error during integer conversion " + e);
            }
            return true;
        }
    }

    public static class quit implements Command{
        @Override
        public String name() {
            return "Quit";
        }
        @Override
        public boolean run(Scanner Scanner) {
           System.exit(0);
           return true;
        }
    }


    public static class freq implements Command
    {
        @Override
        public String name() {
            return "Freq";
        }
        public String resolve_path(Path path_file){
            String sortie = "";
            try
            {
                String content = Files.readString(path_file);
                Dictionary<String, Integer> dic = new Hashtable<String , Integer>();
                LinkedHashMap<String, Integer> exit = new LinkedHashMap<>();
                String[] words = content.split("[\\s']");

                for(int i=0; i<words.length; i++) {
                    var res = dic.get(words[i]);
                    if(res == null) {
                        dic.put(words[i], 1);
                    }
                    else{
                        dic.put(words[i], dic.get(words[i])+1);
                    }
                }
                ((Hashtable<String, Integer>) dic).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> exit.put(x.getKey(), x.getValue()));
                sortie = exit.keySet().toArray()[0].toString()+" "+exit.keySet().toArray()[1].toString()+" "+exit.keySet().toArray()[2].toString();
                //System.out.println(sortie);
            }
            catch (IOException e) {
                System.out.println("Unable to read file:" + e.getMessage());
            }
            return sortie;
        }
        @Override
        public boolean run(Scanner scanner) {
            try {
                System.out.println("Path of file to read to : ");
                String line = scanner.nextLine();
                var path_file = Paths.get(line);
                System.out.println(resolve_path(path_file));
                return true;
            }
            catch (InvalidPathException ex)
            {
                System.out.println("Unreadable file "+ex.getMessage());
                return false;
            }

        }
    }
    public static class predict implements Command
    {
        @Override
        public String name() {
            return "Predict";
        }
        public String resolve_predict(Path path_file, String user_input){
            String sortie = user_input;
            try
            {
                String content = Files.readString(path_file);
                Dictionary<String, Integer> dic = new Hashtable<String , Integer>();
                LinkedHashMap<String, Integer> exit = new LinkedHashMap<>();
                String[] words = content.split("[\\s']");

                if(!content.contains(user_input))
                {
                    System.out.println("Error, le text ne contient pas le mot recherché !");
                    System.exit(0);
                }
                for(int i=0; i<words.length; i++) {
                    var res = dic.get(words[i]);
                    if(res == null) {
                        dic.put(words[i], 1);
                    }
                    else{
                        dic.put(words[i], dic.get(words[i])+1);
                    }
                }
                for(int j=0; j<words.length; j++)
                {
                    dic.put(words[j], (dic.get(words[j])*100)/words.length);
                }
                ((Hashtable<String, Integer>) dic).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> exit.put(x.getKey(), x.getValue()));
                String[] tmp;
                do
                {
                    for(int h=0; h<exit.size(); h++)
                    {
                        sortie += " " + exit.keySet().toArray()[h]+" ";
                    }
                    tmp = sortie.split("[\\s']");

                }while(tmp.length < 20);
                //sortie = exit.keySet().toArray()[0].toString()+" "+exit.keySet().toArray()[1].toString()+" "+exit.keySet().toArray()[2].toString();
                //System.out.println(exit);
                System.out.println("Sortie : "+sortie);

            }
            catch (IOException e) {
                System.out.println("Unable to read file:" + e.getMessage());
            }
            return sortie;
        }
        @Override
        public boolean run(Scanner scanner) {
            try {
                System.out.println("Path of file to read to : ");
                String line = scanner.nextLine();
                var path_file = Paths.get(line);
                System.out.println("Choissisez un mot :");
                String user_input = scanner.nextLine();
                System.out.println(resolve_predict(path_file, user_input));
                return true;
            }
            catch (InvalidPathException ex)
            {
                System.out.println("Unreadable file "+ex.getMessage());
                return false;
            }

        }
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        List<String> commands = new ArrayList<>();
        commands.add("predict");
        commands.add("fibo");
        commands.add("quit");
        commands.add("freq");

        String input = "";
        while(!"quit".equals(input)){
            input = scanner.nextLine();
            if(commands.contains(input)) {
                if ("predict".equals(input)) {
                    boolean run = new predict().run(scanner);
                    if (run) {
                        System.exit(0);
                    }
                } else if ("fibo".equals(input)) {
                    boolean run = new fibo().run(scanner);
                    if (run) {
                        System.exit(0);
                    }
                } else if ("freq".equals(input)) {
                    boolean run = new freq().run(scanner);
                    if (run) {
                        System.exit(0);
                    }
                } else if ("quit".equals(input)) {
                    boolean run = new quit().run(scanner);
                    if (run) {
                        System.exit(0);
                    }
                }
            }
            else
            {
                System.out.println("Unknown command");
            }
        }
    }
}
