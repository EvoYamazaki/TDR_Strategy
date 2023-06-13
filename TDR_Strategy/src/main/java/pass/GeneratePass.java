package pass;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePass {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("test1234mot"));
    }
}