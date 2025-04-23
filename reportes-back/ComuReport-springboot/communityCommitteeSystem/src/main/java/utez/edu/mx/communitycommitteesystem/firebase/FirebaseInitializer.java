package utez.edu.mx.communitycommitteesystem.firebase;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FirebaseInitializer {
    private static final Logger logger = LogManager.getLogger(FirebaseInitializer.class);

    @PostConstruct
    private void initFirestore() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("private-key-firestore.json");

        FirebaseOptions options =  FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }
    }


    public Firestore getFirestore(){
        return FirestoreClient.getFirestore();
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("places-36635.appspot.com", fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("private-key-firestore.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));


        String urlDownload = "https://firebasestorage.googleapis.com/v0/b/places-36635.appspot.com/o/%s?alt=media";
        return String.format(urlDownload, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String url = this.uploadFile(file, fileName);
            Files.delete(file.toPath());
            return url;
        } catch (Exception e) {
            logger.warn(e);
            return "Image couldn't upload, Something went wrong";

        }
    }

    public void delete(String imageUrl) {

        // Obtén la ID del blob de la URL de la imagen
        String bucketName = "places-36635.appspot.com"; // Nombre de tu bucket de Firebase Storage

        // La URL de la imagen podría contener parámetros adicionales, por lo que necesitamos extraer solo la parte relevante
        String blobName = imageUrl.substring(imageUrl.indexOf("./") + "./".length());

        // Inicializa la conexión a Firebase Storage
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Crea la ID del blob
        BlobId blobId = BlobId.of(bucketName, blobName);

        // Elimina el blob
        storage.delete(blobId);
    }
}