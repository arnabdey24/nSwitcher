public class Model {

    private String name;
    private String nodeVersion;
    private String npmVersion;

    private String ngVersion;

    public Model(String name, String nodeVersion, String npmVersion, String ngVersion) {
        this.name = name;
        this.nodeVersion = nodeVersion;
        this.npmVersion = npmVersion;
        this.ngVersion = ngVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public String getNpmVersion() {
        return npmVersion;
    }

    public void setNpmVersion(String npmVersion) {
        this.npmVersion = npmVersion;
    }

    public String getNgVersion() {
        return ngVersion;
    }

    public void setNgVersion(String ngVersion) {
        this.ngVersion = ngVersion;
    }

    @Override
    public String toString() {
        return name+"{" +
                "node: " + nodeVersion +
                ", npm: " + npmVersion +
                ", ng: " + ngVersion +
                '}';
    }
}
