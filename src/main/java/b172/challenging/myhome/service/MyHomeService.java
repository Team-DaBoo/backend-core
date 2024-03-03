package b172.challenging.myhome.service;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.myhome.domain.MyHome;
import b172.challenging.myhome.repository.MyHomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyHomeService {

    private final MyHomeRepository myHomeRepository;

    public MyHome findFirstMyhome() {
        Optional<MyHome> optionalMyHome = myHomeRepository.findById(1L);
        return optionalMyHome.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_HOME));
    }
}
