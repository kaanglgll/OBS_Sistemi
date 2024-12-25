package org.example.demo1;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Dosya işlemlerini gerçekleştirmek için FileManager sınıfı oluşturulur
public class FileManager {
    //Üzerinde işlem yapılacak dosyayı tanımlıyoruz.Final olması sınıf oluşturulurken atanması ve
    // sonradan değiştirilemeyeceği anlamına gelir
    private final String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }
   //öğrencileri dosyaya kaydetmeyi sağlar
    public void saveStudentsToFile(List<Cars> students) {
        //BufferedWriter sınıfı veriyi belli bir miktar biriktirip ondan sonra dosyaya yazar.Daha verimli bir kullanım sağlar
        //new FileWriter(fileName)//fileName ismindeki dosya oluşturulur ve yazma işlemi başlar
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Cars student : students) {
                writer.write(student.getName() + "," + student.getBaseCode() + "," +
                        student.getImage() + "," + student.getDate());
                //her öğrenci bilgisi yeni satıra yazılır
                writer.newLine();
            }
            //hata olma durumunda devreye girer
        } catch (IOException e) {
            //hatanın nereden kaynaklandığını yazdırır
            e.printStackTrace();
        }
    }
    //dosyadaki öğrenci bilgileri okur ve her öğrenci için student nesnesi oluşturur
    //bu nesneler listede toplanır
    public List<Cars> loadStudentsFromFile() {
        //Başlangıçta boş bir liste oluşturulur
        List<Cars> students = new ArrayList<>();
        // Verilen dosya adı (fileName) ile dosyayı okuma işlemi başlatılır. FileReader, dosyayı karakter bazında okur.
        //bufferreader okuma işlemini daha verimli hale getirir
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            //dosyadan her okunan değeri tutan değişken tanımladık
            String line;
            //reader.readLine()-->Dosyadan bir satır okur ve bunuu line değişkenine atar
            while ((line = reader.readLine()) != null) {
                //split-->virgüllerden ayırır ve bunu parts adındaki değişkene atar
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String surname = parts[1];
                    String department = parts[2];
                    //numara string olarak okunur ancak parseInt ile int e dönüştürülür
                    int number = Integer.parseInt(parts[3]);
                    students.add(new Cars(name, surname, department, number));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı, yeni bir dosya oluşturulacak.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}

