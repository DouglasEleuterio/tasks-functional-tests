package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    private WebDriver acessarAplicacao(){
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver/chromedriver"); //Caminho do chromeDriver realizado o Download
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks");
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso(){
        WebDriver driver = acessarAplicacao();
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); //Aguardando 10 Segundos para disparar a ação.
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030"); //Alerte-se para data futura

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar Mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();//Pegando o texto do label
            Assert.assertEquals("Success!", message);
        }finally {
            //Fechar o Browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao(){
        WebDriver driver = acessarAplicacao();
        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030"); //Alerte-se para data futura

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar Mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();//Pegando o texto do label
            Assert.assertEquals("Fill the task description", message);
        }finally {
            //Fechar o Browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData(){
        WebDriver driver = acessarAplicacao();
        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar Mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();//Pegando o texto do label
            Assert.assertEquals("Fill the due date", message);
        }finally {
            //Fechar o Browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada(){
        WebDriver driver = acessarAplicacao();
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); //Aguardando 10 Segundos para disparar a ação.
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("24/11/2020"); //Alerte-se para data futura

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar Mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();//Pegando o texto do label
            Assert.assertEquals("Due date must not be in past", message);
        }finally {
            //Fechar o Browser
            driver.quit();
        }
    }
}
