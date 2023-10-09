public class DeviceInfo {

    public static String getOS() {

        if(System.getProperty("os.name").toLowerCase().startsWith("mac")){
            return "darwin";
        }else if(System.getProperty("os.name").toLowerCase().startsWith("linux")){
            return "linux";
        }else{
            return "";
        }
    }

    public static String getArchitecture(){
        if(System.getProperty("os.arch").toLowerCase().contains("64")){
            return "x64";
        }else if(System.getProperty("os.arch").toLowerCase().contains("32")){
            return "x32";
        } else{
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(getArchitecture());
    }
}
