package org.zerock.springex.sample;


import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@ToString
@RequiredArgsConstructor
public class SampleService {
    // SampleDAO 객체를 SampleService 안에 주입
    // @Autowired
    // SampleDAOImpl를 주입함    
    @Qualifier("normal")
    private final SampleDAO sampleDAO;


}
