package b172.challenging.common.component;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class S3FileUploadUtil {
    private final AmazonS3Client amazonS3Client;

    @Value("${cdn.path.base}")
    private String cdnPathBase;

    @Value("${cdn.url}")
    private String cdnUrl;

    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;

    public S3FileUploadUtil(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String fileUpload(MultipartFile file, String imgUrl){
        if(file == null || file.isEmpty()){
            if(imgUrl != null && imgUrl.isBlank()){
                throw new CustomRuntimeException(Exceptions.INVALID_REQUEST);
            } else {
                return imgUrl;
            }
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setExpirationTime(DateTime.now().plusDays(1).toDate());

        String fileName = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDateTime.format(formatter);

        try {
            amazonS3Client.putObject(new PutObjectRequest(s3Bucket + cdnPathBase + "/" + formattedDate, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.Private)
            );
            // https://
            return cdnUrl + cdnPathBase + "/" + formattedDate + "/" + fileName;
        } catch (IOException ie){
            ie.printStackTrace();
            throw new CustomRuntimeException(Exceptions.NOT_UPLOAD_IMAGE);
        }
    }
}
