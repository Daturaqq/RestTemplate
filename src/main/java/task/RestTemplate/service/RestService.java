package task.RestTemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import task.RestTemplate.model.User;

import java.net.URISyntaxException;
import java.util.Collections;

@Service
public class RestService {

    private RestTemplate restTemplate;

    @Autowired
    public RestService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    private String url = "http://91.241.64.178:7081/api/users";
    private String urlWithId = "http://91.241.64.178:7081/api/users/3";
    private String cookies;

    public User[] getUsersWithCookies() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
        cookies = response.getHeaders().get("Set-Cookie").get(0);
        return response.getBody();
    }

    public String createUser() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", cookies);
        User user = new User((long) 3, "James", "Brown", (byte) 73);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.print("Code: " + response.getBody());
        if(response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public String updateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", cookies);
        User user = new User((long) 3,"Thomas", "Shelby", (byte)50);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.PUT, entity, String.class, 3
        );
        System.out.print(response.getBody());
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public String deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Cookie", cookies);
        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                urlWithId, HttpMethod.DELETE, entity, String.class
        );
        System.out.print(response.getBody());
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
