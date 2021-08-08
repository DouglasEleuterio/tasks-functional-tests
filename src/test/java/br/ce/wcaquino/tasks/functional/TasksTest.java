package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    private WebDriver acessarAplicacao() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "D:\\C\\devops\\chromedriver\\chromedriver.exe");
        //WebDriver driver = new ChromeDriver();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.0.104:8001/tasks");
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); //Aguardando até 3 Segundos para disparar a ação.
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
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
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
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
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
