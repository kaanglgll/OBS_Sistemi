package org.example.demo1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//öğrenci bilgilerini yönetmek için manager sınıfı oluşturuyoruz
public class StudentManager {
    //Öğrencilerin saklanacağı bir List tanımlanıyor.
    //private olduğundan, doğrudan erişim mümkün değil. Kontrol getter ve setter metotları üzerinden sağlanır.
    //Student sınıfından ismi students olan list oluşturuyoruz
    private List<Cars> students;

    //listeyi getirmeyi sağlar
    public List<Cars> getStudents() {
        return students;
    }

    //listeye erişim sağlar
    public void setStudents(List<Cars> students) {
        this.students = students;
    }

    //StudentManager sınıfından bir nesne oluşturulduğunda, students listesi boş bir ArrayList olarak başlatılır.
   public StudentManager(){
       students=new ArrayList<>();
   }

   public int addStudent(Cars student){
	    // Öğrencinin numarasının benzersiz olup olmadığını kontrol et
	    boolean exists = students.stream()
	                             .anyMatch(s -> s.getDate() == student.getDate());

	    if (exists) {return -1;} 
	    else {
	        students.add(student);
	        System.out.println("Öğrenci eklendi: " + student.getName());
	        return 0;
	    }
   }
   //removeIf: Lambda ifadesi ile belirli bir koşulu sağlayan öğeleri siler
   public void deleteStudent(int numberStudent){
       students.removeIf(student -> student.getDate()==numberStudent);
   }
   //Öğrencileri numaralarına göre artan sırada sıralar.
   //Comparator.comparingInt(Student::getNumber): Öğrencilerin getNumber değerine göre sıralama kriteri tanımlar.
    //Collections.sort-->Belirli bir kurala göre sıralar
    //Comparator-->javada iki nesneyi karşılaştırmayı sağlar
    //comparaingInt-->int değere göre karşılaştırır
    //öğrencilerin numaralarını getirir
    public void sortingStudent() {
        //Collections.sort(students,Comparator.comparing(Student::getName));
        Collections.sort(students, Comparator.comparingInt(Cars::getDate));
    }
    public void updateStudent(int studentNumber, String newName, String newSurname, String newDepartment) {
        students.removeIf(student -> student.getDate() == studentNumber);
        students.add(new Cars(newName, newSurname, newDepartment, studentNumber));
    }
    public Cars searchStudentByNumber(int studentNumber ) {
        for (Cars student : students) {
            if (student.getDate() == studentNumber) {
                return student;
            }
        }
        return null; // Öğrenci bulunamadı
    }
}
