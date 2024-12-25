package org.example.demo1;

public class Cars {
    private String name;
    private String baseCode;
    private String image;
    private int date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String surname) {
        this.baseCode = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String department) {
        this.image = department;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int number) {
        if (number<=0){
            //numara pozitif değilse hata mesajı verir
            throw new IllegalArgumentException("Öğrenci numarası pozitif bir değer olmalıdır.");
        }
        this.date = number;
    }
    public Cars(String name,String surname,String department,int number){
        this.name=name;
        this.baseCode=surname;
        this.image=department;
        setDate(number);//hata kontrolü için
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + baseCode + '\'' +
                ", department='" + image + '\'' +
                ", number=" + date +
                '}';
    }

}
