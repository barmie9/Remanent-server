package com.example.demo.controller;

import com.example.demo.controller.request.ProductStatusRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import com.example.demo.repository.ProductStatusRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductStatusRepository productStatusRepository;
//    private boolean isWebOpen = false;
    WebDriver driver;

    @GetMapping("/api/product/{eanCode}") // TODO usunąć mechanizm pobierania z internetu danych "getProductFromWeb()" OK
    public ResponseEntity<Product> getProductByCode(@PathVariable String eanCode){
        Product product = productService.getProductByCode(eanCode);
        if(product != null){
            return ResponseEntity.ok(product);
        }
        else
        {
            Product emptyProduct = new Product();
            emptyProduct.setId(-1L);
            return ResponseEntity.ok(emptyProduct); //TODO do sprawdzenia, może lepiej zwrócić cos innego, gdy nie ma w DB
        }


    }

    @PostMapping("/api/add-product")
    public ResponseEntity<ProductStatus> addProduct(@RequestBody ProductStatusRequest productStatusRequest){ //TODO do poprawy. ok?
        return ResponseEntity.ok(productService.addProduct(productStatusRequest));
    }

    @GetMapping("/api/make-raport/{year}") // TODO do poprawy (zmiana tabel w DB)
    public ResponseEntity<HashMap<String,String>> makeRaport(@PathVariable Integer year){

        List<ProductStatus> productStatus = productStatusRepository.findAllByYear(year).orElse(null);
        Product product;
        HashMap<String,String> response = new HashMap<>();

        if(productStatus == null){
            response.put("response","BLAD - PUSTA LISTA");
            ResponseEntity.ok(response);
        }

        int length = productStatus.size();
        try {
            // Tworzenie nowego arkusza i pliku
            WritableWorkbook workbook = Workbook.createWorkbook(new File("RAPORTY/"+String.valueOf(length) + "-raport.xls"));
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            // Dodawanie zawartości
            Label label = new Label(0, 0, "NAZWA");
            sheet.addCell(label);
            label = new Label(1, 0, "JEDNOSTKA");
            sheet.addCell(label);
            label = new Label(2, 0, "ILOSC");
            sheet.addCell(label);
            label = new Label(3, 0, "CENA");
            sheet.addCell(label);
            for(int i=1 ; i<=length; i++){
                ProductStatus productStat = productStatus.get(i-1);
//                product = productService.getProductById(productStat.getId());
                product = productStat.getProduct();
                label = new Label(0, i, product.getName());
                sheet.addCell(label);
                label = new Label(1, i, product.getNetContentUnit());
                sheet.addCell(label);
                label = new Label(2, i, productStat.getQuantity());
                sheet.addCell(label);
                label = new Label(3, i, product.getPrice().toString());
                sheet.addCell(label);
            }


            // Zapis do pliku
            workbook.write();
            workbook.close();
            response.put("response","OK");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("response","BLAD ZAPISU");
        }


        return ResponseEntity.ok(response);
    }

//    @GetMapping("/api/get-product-from-web/{eanCode}")
//    public Product getProductFromWeb(@PathVariable String eanCode) {
//
//
//        if(isWebOpen == false) openWeb();
//
//
//        // Wprowadzenie kodu kreskowego:
//        WebElement inputElement  = driver.findElement(By.id("gtin_number"));
//        inputElement.clear();
//        inputElement.sendKeys(eanCode);
//
////        //Zaznaczenie checkboxa , tylko w przypadku nie logowania!
////        WebElement checkbox = driver.findElement(By.id("acceptTerms"));
////        checkbox.click();
//
//
//        // Klikniecie przycisku:
//        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[2]/form/div[7]/button")); //bez checkboxa
////        WebElement searchButton = driver.findElement(By.xpath("/html/body/div[2]/div/main/div[3]/div/div[2]/form/div[9]/button")); //Z checkboxem
//
//        searchButton.click();
//
//        // todo trzeba chwile poczekac na załadowanie
////        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//////        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elementId")));
////        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[4]/div[1]/div[2]/div/div/div[2]")));
////        WebElement brandName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[4]/div[1]/div[2]/div/div/div[3]")));
////        WebElement category = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[4]/div[1]/div[2]/div/div/div[4]")));
//
//
//        try {
//            Thread.sleep(500); // oczekiwanie przez 5 sekund
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        WebElement name = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[4]/div[1]/div[2]/div/div/div[2]"));
//        WebElement brandName = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[4]/div[1]/div[2]/div/div/div[3]"));
//        WebElement category = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[3]/div/div[4]/div[1]/div[2]/div/div/div[4]"));
//
//        String strName = name.getText();
//        String strBrandName = brandName.getText();
//        String strCategory = category.getText();
//
//        if(strName.contains("Numer GTIN")) strName= "-";
//
//        Product product = new Product();
//        product.setId(0l);
//        product.setName(strName);
//        product.setBrandName(strBrandName);
//        product.setCategory(strCategory);
//        return product;
//
//
////            return "Page Title: " + strName + " - "+ strBrandName +" - " +  strCategory;
//    }



//    public void openWeb(){
//
//        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
//        driver = new FirefoxDriver();
//        driver.get("https://www.eprodukty.gs1.pl/catalog");
//
//        // Zaakceptowanie cookie
//        WebElement element = driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
//        element.click();
//
//        // Logowanie
//        WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/main/div[1]/header/div/div[2]/div/button[1]"));
//        loginButton.click();
//
//        WebElement email = driver.findElement(By.id("email"));
//        email.clear();
//        email.sendKeys("aneta.mierzwinska.75@gmail.com");
//
//        WebElement password = driver.findElement(By.id("password"));
//        password.clear();
//        password.sendKeys("P8iaV!gUcPxnbTP");
//
//        WebElement logButton = driver.findElement(By.xpath("/html/body/div[2]/div/main/div[3]/div/div/div[1]/form/div[4]/button"));
//        logButton.click();
//
//        // todo trzeba chwile poczekac na zalogowanie (Do sprawdzenia)
//        try {
//            Thread.sleep(1500); // oczekiwanie przez 5 sekund
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // Nawigacja do katalogu
//        driver.get("https://www.eprodukty.gs1.pl/catalog");
//
//        isWebOpen = true;
//
//    }
}
