package util;

import BE.Events;
import BE.Users.User;

import javax.net.ssl.SSLContext;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class HttpService {


//Metoden tager den random ass string som bliver lavet ind i en barcode som content, og filename står for hvad billedet den gammer skal hedde
    //det er stærkt anbefalet at man bruger det samme string i filename som man gjorde i content, det gør det en del nemmere at finde barcodesne senere
    public String generateBarcode(Events event)
    {
        try{

            Random random = new Random();

            String realContent = String.valueOf(random.nextInt(1000000,9999999));
            String filename ="E" + realContent + ".png";
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(SSLContext.getDefault())
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://barcodeapi.org/api/" + realContent))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if(response.statusCode() == 200)
            {
                byte[] responseBody = response.body();
                FileOutputStream outputStream = new FileOutputStream(filename);
                outputStream.write(responseBody);
                outputStream.close();
                return realContent;
            }
            else {
                System.out.println("failed to generate barcode, respone code:" + response.statusCode());
                return null;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateBarcodeDiscount(Events event)
    {
        try{
            String filename ="T" + event.getEventName() + ".png";
            Random random = new Random();

            String realContent = String.valueOf(random.nextInt(1000000,9999999));
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(SSLContext.getDefault())
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://barcodeapi.org/api/" + realContent))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if(response.statusCode() == 200)
            {
                byte[] responseBody = response.body();
                FileOutputStream outputStream = new FileOutputStream(filename);
                outputStream.write(responseBody);
                outputStream.close();
                System.out.println("Barcody generated sucessfully");
                return realContent;
            }
            else {
                System.out.println("failed to generate barcode, respone code:" + response.statusCode());
                return null;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateBarcodeFood(Events event)
    {
        try{

            String filename = "F" + event.getEventName() + ".png";
            Random random = new Random();

            String realContent = String.valueOf(random.nextInt(1000000,9999999));
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(SSLContext.getDefault())
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://barcodeapi.org/api/" + realContent))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if(response.statusCode() == 200)
            {
                byte[] responseBody = response.body();
                FileOutputStream outputStream = new FileOutputStream(filename);
                outputStream.write(responseBody);
                outputStream.close();
                System.out.println("Barcody generated sucessfully");
                return realContent;
            }
            else {
                System.out.println("failed to generate barcode, respone code:" + response.statusCode());
                return null;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateBarcodeDrink(Events event)
    {
        try{

            String filename = "D" + event.getEventName() + ".png";
            Random random = new Random();

            String realContent = String.valueOf(random.nextInt(1000000,9999999));
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(SSLContext.getDefault())
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://barcodeapi.org/api/" + realContent))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if(response.statusCode() == 200)
            {
                byte[] responseBody = response.body();
                FileOutputStream outputStream = new FileOutputStream(filename);
                outputStream.write(responseBody);
                outputStream.close();
                System.out.println("Barcody generated sucessfully");
                return realContent;
            }
            else {
                System.out.println("failed to generate barcode, respone code:" + response.statusCode());
                return null;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
