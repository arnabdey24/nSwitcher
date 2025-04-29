import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Switcher {


    public static ProcessBuilder builder;
    public static Process p;

    public static Data data;

    public static void main(String[] args) throws IOException, InterruptedException {

/*


curl -o node-v16.20.0-linux-x64.tar.xz https://nodejs.org/dist/v16.20.0/node-v16.20.0-linux-x64.tar.xz;
tar -xf node-v16.20.0-linux-x64.tar.xz;
sudo mv node-v16.20.0-linux-x64 /usr/local/;

sudo rm -rf /usr/local/bin/node && echo "node unhooked..";
sudo rm -rf /usr/local/bin/npm && echo "npm unhooked..";
sudo rm -rf /usr/local/bin/ng && echo "ng unhooked..";
sudo ln -s /usr/local/node-v16.20.0-linux-x64/bin/node /usr/local/bin/node && echo "node hooked..";
sudo ln -s /usr/local/node-v16.20.0-linux-x64/bin/npm /usr/local/bin/npm && echo "npm hooked..";
sudo ln -s /usr/local/node-v16.20.0-linux-x64/bin/ng /usr/local/bin/ng && echo "ng hooked..";
echo "node version:";
node -v;
echo "npm version:";
npm -v;
echo "angular version:";
ng version;


sudo update-alternatives --config java

find / -name ng

ng v | grep 'Angular CLI:'

*/
        data=new Data();

        String os=DeviceInfo.getOS();
        String architecture=DeviceInfo.getArchitecture();

        String nodeVersion;
        String npmVersion;
        String ngVersion;

        boolean savedProject=false;

        Scanner input = new Scanner(System.in);


        getSudoPrivilege();

        printBanner();

        System.out.print("Select existing project environment[Y/n]: ");

        String opt="Y";
        opt=input.nextLine();

        if (!Objects.equals(opt, "") && opt.toLowerCase().charAt(0) == 'n') {
            System.out.print("Node version: ");
            nodeVersion = input.nextLine(); //validate version input

            System.out.print("Npm version: ");
            npmVersion = input.nextLine(); //validate version input

            System.out.print("Angular version: ");
            ngVersion = input.nextLine(); //validate version input
        } else {

            System.out.println();

            for (int i = 0; i < data.size(); i++) {
                System.out.println(i+") "+data.get(i));
            }

            System.out.print("Select Project: ");
            int n=Integer.parseInt(input.nextLine());

            nodeVersion=data.get(n).getNodeVersion();
            npmVersion=data.get(n).getNpmVersion();
            ngVersion=data.get(n).getNgVersion();

            savedProject=true;
        }


        //---------downloading section----------

        System.out.println("Fetching necessary files...");

        exec("curl", "-o", "node-v" + nodeVersion + "-"+os+"-"+architecture+".tar.xz", "https://nodejs.org/dist/v" + nodeVersion + "/node-v" + nodeVersion + "-"+os+"-"+architecture+".tar.xz");
        exec("tar", "-xf", "node-v" + nodeVersion + "-"+os+"-"+architecture+".tar.xz");
        exec("sudo", "mv", "node-v" + nodeVersion + "-"+os+"-"+architecture, "/usr/local/");

        System.out.println("Fetching necessary files completed.");

        //----------unhook section-----------

        System.out.println("Dropping previous versions...");

        exec("sudo", "rm", "-rf", "/usr/local/bin/node");
        exec("sudo", "rm", "-rf", "/usr/local/bin/npm");
        exec("sudo", "rm", "-rf", "/usr/local/bin/npx");
        exec("sudo", "rm", "-rf", "/usr/local/bin/ng");

        System.out.println("Previous versions dropped.");

        //----------hook section-----------

        System.out.println("Globally changing node and npm version.");

        exec("sudo", "ln", "-s", "/usr/local/node-v" + nodeVersion + "-"+os+"-"+architecture+"/bin/node", "/usr/local/bin/node");
        exec("sudo", "ln", "-s", "/usr/local/node-v" + nodeVersion + "-"+os+"-"+architecture+"/bin/npm", "/usr/local/bin/npm");
        exec("sudo", "ln", "-s", "/usr/local/node-v" + nodeVersion + "-"+os+"-"+architecture+"/bin/npx", "/usr/local/bin/npx");

        System.out.print("Global node version: ");
        exec("node", "-v");
        System.out.print("Global npm version: ");
        exec("npm", "-v");
        System.out.print("Global npx version: ");
        exec("npx", "-v");

        //---------change npm and ng version as per request ---------

        System.out.println("Changing npm and angular/cli version to specified version...");

        exec("npm", "install", "-g", "npm@" + npmVersion);

        exec("sudo", "ln", "-s", "/usr/local/node-v" + nodeVersion + "-"+os+"-"+architecture+"/bin/npm", "/usr/local/bin/npm");

        System.out.print("Global npm version changed to version: ");
        exec("npm", "-v");

        exec("npm", "install", "-g", "@angular/cli@" + ngVersion);

        exec("sudo", "ln", "-s", "/usr/local/node-v" + nodeVersion + "-"+os+"-"+architecture+"/bin/ng", "/usr/local/bin/ng");

        System.out.print("Global angular/cli version changed to version: ");
        exec("ng", "v"); //ng v | grep 'Angular CLI:'

        //--------remove downloaded files---------------
        exec("rm", "-r", "node-v" + nodeVersion + "-"+os+"-"+architecture);

        exec("rm", "node-v" + nodeVersion + "-"+os+"-"+architecture+".tar.xz");

        if(!savedProject){
            System.out.print("Save the project environment[Y/n]: ");

            opt="Y";
            opt=input.nextLine();

            if (Objects.equals(opt, "") || opt.toLowerCase().charAt(0) != 'n') {
                System.out.print("Project name: ");
                String name=input.nextLine();

                data.add(new Model(name,nodeVersion,npmVersion,ngVersion));
                data.save();
                System.out.println("Saved");
            }
        }

        System.out.println("See you soon...");

    }

    private static void getSudoPrivilege() throws IOException, InterruptedException {
        builder = new ProcessBuilder("sudo", "-S", "echo", "");
        builder.inheritIO();
        p = builder.start();
        p.waitFor();

        builder = new ProcessBuilder("sudo", "echo", "");
        builder.inheritIO();
        p = builder.start();
        p.waitFor();
    }

    public static void exec(String... args) throws IOException, InterruptedException {
        builder = new ProcessBuilder(args);
        builder.inheritIO();
        p = builder.start();
        p.waitFor();
    }

    private static void printBanner() {
        System.out.println("   ______          _ _       _             __  \n" +
                "  / / ___|_      _(_) |_ ___| |__   ___ _ _\\ \\ \n" +
                " / /\\___ \\ \\ /\\ / / | __/ __| '_ \\ / _ \\ '__\\ \\\n" +
                " \\ \\ ___) \\ V  V /| | || (__| | | |  __/ |  / /\n" +
                "  \\_\\____/ \\_/\\_/ |_|\\__\\___|_| |_|\\___|_| /_/ \n" +
                "author: arnabdey24.github.io               v0.1\n"
        );
    }
}
