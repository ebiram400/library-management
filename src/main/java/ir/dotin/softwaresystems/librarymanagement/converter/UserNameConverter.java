package ir.dotin.softwaresystems.librarymanagement.converter;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.ByteBuffer;

@Converter
public class UserNameConverter implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String userName) {
        AWSKMS kmsClient = KmsClientSingleton.getInstance();

        // رمزگذاری رشته کاربر به بایت
        byte[] userNameBytes = userName.getBytes();
        EncryptRequest request = new EncryptRequest()
                .withKeyId("arn:aws:kms:region:account-id:key/key-id")
                .withPlaintext(ByteBuffer.wrap(userNameBytes));

        return kmsClient.encrypt(request).getCiphertextBlob().array();
    }

    @Override
    public String convertToEntityAttribute(byte[] encryptedBytes) {
        AWSKMS kmsClient = KmsClientSingleton.getInstance();

        // رمزگشایی بایت‌ها
        ByteBuffer ciphertext = ByteBuffer.wrap(encryptedBytes);
        DecryptRequest request = new DecryptRequest().withCiphertextBlob(ciphertext);

        ByteBuffer plaintextBuffer = kmsClient.decrypt(request).getPlaintext();

        // استخراج رشته از بایت
        return new String(plaintextBuffer.array());
    }
}
