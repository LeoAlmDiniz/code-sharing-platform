package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.dto.CodeDto;
import platform.dto.UuidDto;
import platform.model.Code;
import platform.service.CodeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    @Autowired
    private CodeService codeService;

    public ApiController(CodeService codeService) {
        this.codeService = codeService;
    }


    @GetMapping(value = "/api/code/{id}")
    public ResponseEntity<?> getCode(@PathVariable UUID id) {
        System.out.println(codeService.getCodeById(id));
        codeService.updateCodeById(id);
        System.out.println(codeService.getCodeById(id));
        if (codeService.getCodeById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok( new CodeDto( codeService.getCodeById(id).get()) );
    }

    @GetMapping(value = "/api/code/latest")
    public ResponseEntity<List<CodeDto>> getCodeLatest() {
        List<CodeDto> listDto = new ArrayList<>();
        for (Code code : codeService.getCodeList()) {
            if (!code.isRestricted()) {
                listDto.add(new CodeDto( code ) );
            }
        }
        listDto = listDto.stream().skip(Math.max(0, listDto.size()-10)).collect(Collectors.toList());
        Collections.reverse(listDto);
        return ResponseEntity.ok( listDto );
    }

    @PostMapping(value = "/api/code/new")
    public ResponseEntity<UuidDto> addNewCode(@RequestBody CodeDto codeDto) {
        Code newCode = new Code(codeDto.getCode(), codeDto.getTime(), codeDto.getViews());
        codeService.saveCode(newCode);
        System.out.println(newCode);
        return ResponseEntity.ok(new UuidDto(newCode));
    }


}