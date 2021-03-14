package com.snz1.seatas.storage;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

/**
 * 应用启动类
 */

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAutoConfiguration
@SpringBootApplication
@com.snz1.annotation.Snz1dpApplication //启用Snz1p应用配置
@com.snz1.annotation.EnableDruid // 启用Druid数据源
@com.snz1.annotation.EnableAutoCaching //启用默认缓存配置
@com.snz1.annotation.EnableWebMvc // 启用Mvc默认配置
@com.snz1.annotation.EnableAutoScheme // 自动构建数据库
public class Application {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setBanner(new ApplicationBanner());
    app.run(args);
  }

  private static class ApplicationBanner implements Banner {

    // figlet Storage |sed -e "s/\\\\/\\\\\\\\/g" | sed -e "s/^/\"/" | sed -e "s/\$/\", \/\//g"
    private static final String[] BANNER = { //
      " ____  _                             ", //
      "/ ___|| |_ ___  _ __ __ _  __ _  ___ ", //
      "\\___ \\| __/ _ \\| '__/ _` |/ _` |/ _ \\", //
      " ___) | || (_) | | | (_| | (_| |  __/", //
      "|____/ \\__\\___/|_|  \\__,_|\\__, |\\___|", //
      "                          |___/      ", //
    };

    private static final String APP_BOOT = ":: Cloud Native Microservice :: ";

    private static final int STRAP_LINE_SIZE = 28;

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {

      for (String line : BANNER) {
        printStream.println(line);
      }
      String version = "1.4.1";
      version = (version == null ? "" : " v" + version + "");
      String padding = "";
      while (padding.length() < STRAP_LINE_SIZE) {
        padding += " ";
      }

      printStream.println(AnsiOutput.toString(AnsiColor.GREEN, APP_BOOT));

      printStream.println(AnsiOutput.toString(AnsiColor.DEFAULT, padding, AnsiStyle.FAINT, version));

      printStream.println();
    }

  }

}
