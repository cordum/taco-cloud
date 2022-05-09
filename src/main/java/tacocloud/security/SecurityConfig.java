package tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tacocloud.model.User;
import tacocloud.repository.UserRepository;

// Сервис получения сведений о пользователе
// Перепишем default config который идет с
// <artifactId>spring-boot-starter-security</artifactId>
// При добавлении этой зависимости:
// Spring Security создает пользователя с именем user и автоматически сгенерированным паролем,  который можно посмотреть в консоли.
// Создается страница с формой для ввода имени и пароля -имеем Form-based аутентификацию.
// Имя и пароль реально проверяются.
// Все url оказываются недоступны, пока мы не «залогинимся» под этим пользователем.
// Создается страница, где можно «разлогиниться». Она находится по адресу logout.
@Configuration
// Для использования @PreAuthorize нужно добавить:
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //4.2.2 JDBC-based user store
//    @Autowired
//    DataSource dataSource;

//  Нужен для создания/авторизации пользователей. Пароль никогда не декодируется. Когда user вводит пароль,
//  пароль кодируется и сравнивается с закодированным значением в БД
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    Можно прописать пользователя и пароль в application.properties
//    Spring Security имеет несколько реализаций интерф UserDetailsService:
//      in-memory
//      JDBC user store
//      LDAP user store

//    @Override
//    2 вариант
//    позволяет настраивать SecurityConfig
//    protected void configure(HttpSecurity http) throws Exception
//    {
////      метод AuthorizationRequests() возвращает объект (ExpressionInterceptUrlRegistry),
////      в котором можно указать URL-пути, шаблоны и правила безопасности для этих путей
//        http.authorizeRequests()
////               запросы /design и /orders доступны только авторизованным пользователям;
//                .antMatchers("/design", "/orders")
//                .hasRole("ROLE_USER")
////               все остальные запросы должны быть разрешены для всех пользователей
//                .antMatchers("/", "/**").permitAll()
////               порядок этих правил важен.  Правила безопасности, объявленные первыми,
////               имеют приоритет над правилами, объявленными ниже.
////               Сообщаем путь пользовательской страницы входа
//                .and()
//                .formLogin()
//                .loginPage("/login");
//1 вариант
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//               Вызываем Bean созданный на 27 строке (injecting)
//               Казалось бы, мы вызываем метод encoder() и передаем его
//               возвращаемое значение в passwordEncoder(). В действительности,
//               однако, поскольку метод encoder() аннотируется @Bean, он будет
//               использоваться для объявления компонента PasswordEncoder в контексте
//               приложения Spring. Любые вызовы encoder() будут перехвачены,
//               чтобы возвратить экземпляр bean из контекста приложения.
//                .passwordEncoder(encoder());

        //4.2.3 LDAP-based user store
//        auth
//                .ldapAuthentication()
//      По умолчанию сервер LDAP прослушивает порт 33389 на localhost. Но если
//      ваш сервер LDAP находится на другой машине, вы можете использовать
//      метод .contextSource() для настройки его местоположения
//                .contextSource()
//                Удаленный сервер
//                    .url("ldap://tacocloud.com:389/dc=tacocloud,dc=com")
//                Встроенный сервер
//                    .root("dc=tacocloud,dc=com")
//                Загрузка LDIF-файла
//                    .ldif("classpath:users.ldif")
//                .and()
//                    .userSearchBase("ou=people")
//                    .userSearchFilter("(uid={0})")
//                    .groupSearchBase("ou=groups")
//                    .groupSearchFilter("member={0}")
//      Метод .passwordCompare сравнивает пароль введенный пользователем с паролем в БД
//                    .passwordCompare()
//                    .passwordEncoder(new BCryptPasswordEncoder());

//      По умолчанию пароль, указанный в форме входа, будет сравниваться со значением
//      атрибута userPassword в записи LDAP пользователя. Если пароль хранится в другом
//      атрибуте, можно указать имя атрибута пароля с помощью .passwordAttribute("passcode"):

        //4.2.2 JDBC-based user store
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled from Users " +
//                                "where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities " +
//                                "where username=?")
//                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
//      Метод .passwordEncoder() принимает любую реализацию интерфейса PasswordEncoder Spring Security

        // in-memory
//        Первый вариант пользовательского хранилища
//        Простой вариант, для тестирования
//        auth
//                .inMemoryAuthentication()
//                .withUser("buzz")
//                .password("infinity")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("woody")
//                .password("bullseye")
//                .authorities("ROLE_USER");
//        @Bean
//        public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//            List<UserDetails> usersList = new ArrayList<>();
//            usersList.add(new User("buzz", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//            usersList.add(new User("woody", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//            return new InMemoryUserDetailsManager(usersList);
//        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .authorizeRequests()
//                   запросы /design и /orders доступны только авторизованным пользователям (с ролью ROLE_USER)
//                   префикс ROLE_ не указывается
                        .antMatchers("/design", "/orders").hasRole("USER")// ROLE_USER
                        .antMatchers("/", "/**").permitAll()
                    .and()
                        .formLogin()
                            .loginPage("/login")
// Enable both a third-party OAuth2 login and traditional username-password login
                    .and()
                        .oauth2Login()
                            .loginPage("/login")
                    .and()
                        .logout()
                    .and()
                    .build();
        }

        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepo) {
            return username -> {
                User user = userRepo.findByUsername(username);
                if (user != null) return user;
                throw new UsernameNotFoundException("User '" + username + "' not found");
            };
        }

//  csrf
//  Скрытое поле на форме
//  <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>
//    }
}
