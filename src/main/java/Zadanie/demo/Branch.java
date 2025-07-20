package Zadanie.demo;

public class Branch {
    String name;
    String sha;
    Branch(String name, String sha){
        this.name=name;
        this.sha = sha;
    }
    public void SetName(String name){
        this.name=name;
    }
    public void setSha(String sha) {
        this.sha = sha;
    }
    public String getName() {
        return name;
    }
    public String getSha() {
        return sha;
    }
    @Override
    public String toString() {
        return "Branch Name='" + name +
                " ,Last Commit Sha='" + sha + "\n";
    }
}
