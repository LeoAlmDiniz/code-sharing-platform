package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.model.Code;
import platform.repository.CodeRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Iterable<Code> getCodeList() {
        return codeRepository.findAll();
    }

    public Optional<Code> getCodeById(UUID id) { return codeRepository.findById(id); }

    public void updateCodeById(UUID id) {
        if (codeRepository.findById(id).isPresent()) {
            Code newCode = codeRepository.findById(id).get().updateAndReturn();
            codeRepository.save(newCode);
            if (codeRepository.findById(id).get().isToBeDeleted()) {
                codeRepository.deleteById(id);
            }
        }


    }

    public void saveCode(Code code) {
        codeRepository.save(code);
    }

}