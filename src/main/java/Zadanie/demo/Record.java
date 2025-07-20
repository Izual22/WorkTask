package Zadanie.demo;

import java.util.List;

public class Record {
    String name;
    String login;
    List<Branch> branches;
    public Record(String name, String login, List<Branch> branches) {
        this.name = name;
        this.login = login;
        this.branches = branches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "Record{" +
                "Repository Name='" + name + "\n" +
                ", Owner Login='" + login + "\n" +
                ", Repository Branches=" + branches +
                '}'+"\n";
    }
}
