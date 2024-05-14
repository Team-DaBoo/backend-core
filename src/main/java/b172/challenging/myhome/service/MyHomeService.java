package b172.challenging.myhome.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.myhome.domain.MyHome;
import b172.challenging.myhome.repository.MyHomeRepository;

@Service
@RequiredArgsConstructor
public class MyHomeService {

	private final MyHomeRepository myHomeRepository;

	public MyHome findFirstMyhome() {
		Optional<MyHome> optionalMyHome = myHomeRepository.findById(1L);
		return optionalMyHome.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_HOME));
	}
}
