package service1;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "service2", url = "http://localhost:8090", fallback = Service1Client.Service1ClientCallback.class)
public interface Service1Client {

    @GetMapping(value = "/sleep/{time}")
    ResponseEntity sleep(@PathVariable("time") int time);

    @Component
    class Service1ClientCallback implements Service1Client {

        @NewSpan
        @Override
        public ResponseEntity sleep(int time) {
            return new ResponseEntity("Callback\n", HttpStatus.OK);
        }
    }

}
