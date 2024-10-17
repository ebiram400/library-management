package ir.dotin.softwaresystems.librarymanagement.converter;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;

public class KmsClientSingleton {
    private static AWSKMS instance;
    public KmsClientSingleton() {
    }

    public static AWSKMS getInstance() {
        if (instance == null) {
            instance = AWSKMSClientBuilder.standard().build();
        }
        return instance;
    }
}
