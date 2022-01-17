package com.nokhrin;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianOutput;
import com.nokhrin.gui.GWUserForm;
import com.nokhrin.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Configuration
@SpringBootApplication
public class GwclientApplication {

    @Bean
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8081/GWService");
        invoker.setServiceInterface(WordService.class);
        return invoker;
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(
                GwclientApplication.class).headless(false).run(args);

        String name = "";
        WordService w = context.getBean(WordService.class);
        GWUserForm userForm = new GWUserForm(w);
        userForm.setVisible(true);
        while (true) {
            name = userForm.selectUsername();
            if (name == null) {
                System.exit(0);
            }

            if (name != null && !name.isEmpty() && w.checkUsernameAvailability(name)) {
                w.createNewUser(name);
                userForm.setUserName(name);
                break;
            }
            continue;
        }
        userForm.init(name);
        userForm.setUserName(name);
        while (true) {
         //   System.out.println("New game");

                userForm.setDescription(w.getDesc());


            while (!w.isWin()) {


                    userForm.setWord(w.getUnguessedWord());
                    userForm.setScoreLabel(w.getUserScore(name));
                    userForm.setMessage(w.getIngameUserName() + " is in game");

                    userForm.checkButtons(w.getAvailableLetters());
                    if(userForm.isQuit){
                        w.quit(name);
                        System.exit(0);
                    }

            }

                userForm.setWord(w.getUnguessedWord());
                userForm.setScoreLabel(w.getUserScore(name));
                userForm.showMessage(w.getWinner());


        }
    }
}
