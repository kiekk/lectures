package hello.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Set;

@Slf4j
public class CommandLineV2 {
    public static void main(String[] args) {
        // arg 는 key, value 가 아닌 하나의 String 으로 출력
        for (String arg : args) {
            log.info("arg {}", arg);
        }
        /*
            arg --url=devdb
            arg --username=dev_user
            arg --password=dev_pw
         */
        ApplicationArguments appArgs = new DefaultApplicationArguments(args);

        log.info("SourceArgs = {}", List.of(appArgs.getSourceArgs()));
        log.info("NonOptionArgs = {}", appArgs.getNonOptionArgs());
        log.info("OptionNames = {}", appArgs.getOptionNames());

        Set<String> optionNames = appArgs.getOptionNames();
        for (String optionName : optionNames) {
            log.info("option args {}={}", optionName,
                    appArgs.getOptionValues(optionName));
        }

        // --url=devdb --username=dev_user --password=dev_pw mode=on
        // --로 시작하지 않으면 option argument 가 아니다.
        // optionValues 는 key, value 로 출력
        List<String> url = appArgs.getOptionValues("url");
        List<String> username = appArgs.getOptionValues("username");
        List<String> password = appArgs.getOptionValues("password");
        List<String> mode = appArgs.getOptionValues("mode");

        log.info("url={}", url);    // url=[devdb]
        log.info("username={}", username);  // username=[dev_user]
        log.info("password={}", password);  // password=[dev_pw]

        // mode 는 option argument 가 아니므로 null 출력
        log.info("mode={}", mode);  // mode=null

    }
}