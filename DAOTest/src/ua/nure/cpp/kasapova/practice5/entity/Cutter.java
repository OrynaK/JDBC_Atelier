package ua.nure.cpp.kasapova.practice5.entity;

import java.util.Objects;

public class Cutter {
    private int id;
    private String name;
    private String surname;

    public Cutter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cutter cutter = (Cutter) o;
        return id == cutter.id && Objects.equals(name, cutter.name) && Objects.equals(surname, cutter.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }

    @Override
    public String toString() {
        return "Cutter[" +
                "id:" + id +
                ", Full name:'" + name + " " + surname + '\'' +
                ']'+"\n";
    }
}
