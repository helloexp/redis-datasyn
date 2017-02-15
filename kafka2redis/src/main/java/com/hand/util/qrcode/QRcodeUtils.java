package com.hand.util.qrcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.collections.MapUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hand on 2017/2/7.
 */
public class QRcodeUtils {

    private final static String IMG_GIF = "gif";

    private final static String IMG_PNG = "png";

    private final static int IMG_WIDTH = 250;

    private final static int IMG_HEIGHT = 250;

    public static String createQrcode(Map<String,Object> content){
        String result = "";
        if(MapUtils.isEmpty(content)){
            return result;
        }
        try {
            String contentStr = new ObjectMapper().writeValueAsString(content);
            Map<EncodeHintType,Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            hints.put(EncodeHintType.MARGIN,1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contentStr, BarcodeFormat.QR_CODE, IMG_WIDTH, IMG_HEIGHT, hints);
            //存储二维码图片的IO字节Stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,IMG_PNG,byteArrayOutputStream);
            byte[] qrcodeBytes = byteArrayOutputStream.toByteArray();
            result = new BASE64Encoder().encode(qrcodeBytes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String createQrcode(Map<String,Object> content,int height,int width){
        String result ="";
        if(MapUtils.isEmpty(content)||height<=0||width<=0){
            return result;
        }
        try {
            String contentStr = new ObjectMapper().writeValueAsString(content);
            Map<EncodeHintType,Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contentStr, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,IMG_PNG,arrayOutputStream);
            byte[] bytes = arrayOutputStream.toByteArray();
            result = new BASE64Encoder().encode(bytes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public Map<String, Object> convertQRcodeStrToMap(String imgStr) {
        Map<String,Object> qrcodeMsg = null;
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(imgStr);
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(arrayInputStream);
            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(luminanceSource);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Map<DecodeHintType,Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            String qrcodeJson =  result.getText();
            qrcodeMsg = new ObjectMapper().readValue(qrcodeJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return qrcodeMsg;
    }
}
