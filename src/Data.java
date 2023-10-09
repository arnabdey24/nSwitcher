import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Data extends ArrayList<Model>{

    private static final String FILE = "data.txt";

    public Data(){
        super();

        readData();
    }

    private void readData() {
        try {

            File file=new File(FILE);
            file.createNewFile();

            Scanner scanner=new Scanner(new FileReader(FILE));
            while(scanner.hasNextLine()){
                String[] data=scanner.nextLine().split(",");
                Model model=new Model(data[0], data[1], data[2], data[3]);
                add(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            PrintWriter writer=new PrintWriter(FILE);
            for (Model model : this) {
                writer.println(model.getName()+","+model.getNodeVersion()+","+model.getNpmVersion()+","+model.getNgVersion());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
